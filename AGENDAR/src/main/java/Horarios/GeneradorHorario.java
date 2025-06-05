/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Horarios;

import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.modelos.Aula;
import com.mycompany.gestor.modelos.Bloque;
import com.mycompany.gestor.modelos.GrupoHorario;
import com.mycompany.gestor.modelos.Materia;
import com.mycompany.gestor.modelos.Profesor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author Sergio
 */
public class GeneradorHorario {

    /** 36 bloques semanales, generados una sola vez. */
    public static List<Bloque> generarBloques() {
        List<Bloque> bloques = new ArrayList<>();
        int id = 0;
        int[] horas = {6, 8, 10, 12, 14, 16};

        for (int dia = 0; dia < 6; dia++) { // de lunes (0) a sábado (5)
            for (int hora : horas) {
                bloques.add(new Bloque(id++, dia, hora));
            }
        }
        return bloques;
    }

    /** Asigna a cada estudiante los grupos faltantes. (No cambia respecto a antes.) */
    public void asignarGruposFaltantes(Connection conn) throws SQLException {
        String estudiantesQuery = "SELECT id_estudiante, id_carrera, semestre FROM estudiantes";
        PreparedStatement stmtEst = conn.prepareStatement(estudiantesQuery);
        ResultSet rsEst = stmtEst.executeQuery();

        while (rsEst.next()) {
            int idEstudiante = rsEst.getInt("id_estudiante");
            int idCarrera = rsEst.getInt("id_carrera");
            int semestre = rsEst.getInt("semestre");

            String faltantesQuery = """
                SELECT cm.id_materia
                FROM carrera_materias cm
                WHERE cm.id_carrera = ? AND cm.semestre = ?
                AND cm.id_materia NOT IN (
                    SELECT g.id_materia
                    FROM estudiante_grupos eg
                    JOIN grupos g ON eg.id_grupo = g.id_grupo
                    WHERE eg.id_estudiante = ?
                )
            """;

            PreparedStatement stmtFaltantes = conn.prepareStatement(faltantesQuery);
            stmtFaltantes.setInt(1, idCarrera);
            stmtFaltantes.setInt(2, semestre);
            stmtFaltantes.setInt(3, idEstudiante);
            ResultSet rsFaltantes = stmtFaltantes.executeQuery();

            while (rsFaltantes.next()) {
                int idMateria = rsFaltantes.getInt("id_materia");

                // Elegimos el grupo con menos inscritos
                PreparedStatement stmtGrupo = conn.prepareStatement("""
                    SELECT g.id_grupo, COUNT(eg.id_estudiante) AS inscritos
                    FROM grupos g
                    LEFT JOIN estudiante_grupos eg ON g.id_grupo = eg.id_grupo
                    WHERE g.id_materia = ?
                    GROUP BY g.id_grupo
                    ORDER BY inscritos ASC
                """);
                stmtGrupo.setInt(1, idMateria);
                ResultSet rsGrupo = stmtGrupo.executeQuery();

                if (rsGrupo.next()) {
                    int idGrupo = rsGrupo.getInt("id_grupo");
                    PreparedStatement insert = conn.prepareStatement("""
                        INSERT INTO estudiante_grupos (id_estudiante, id_grupo)
                        VALUES (?, ?)
                    """);
                    insert.setInt(1, idEstudiante);
                    insert.setInt(2, idGrupo);
                    insert.executeUpdate();
                } else {
                    System.out.println("No hay grupo para materia " + idMateria
                                       + " (estudiante " + idEstudiante + ")");
                }
            }
        }
    }

    /** Carga todos los grupos existentes con su materia, profesor, carga horaria y lista de estudiantes. */
    public List<GrupoHorario> cargarGrupos(Connection conn) throws SQLException {
        List<GrupoHorario> grupos = new ArrayList<>();

        String query = """
            SELECT g.id_grupo, g.id_materia, g.id_profesor, m.carga_horaria
            FROM grupos g
            JOIN materias m ON m.id_materia = g.id_materia
        """;
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int idGrupo = rs.getInt("id_grupo");
            int idMateria = rs.getInt("id_materia");
            int idProfesor = rs.getInt("id_profesor");
            int cargaHoraria = rs.getInt("carga_horaria");

            
            List<Integer> idEstudiantes = new ArrayList<>();
            PreparedStatement estStmt = conn.prepareStatement(
                "SELECT id_estudiante FROM estudiante_grupos WHERE id_grupo = ?"
            );
            estStmt.setInt(1, idGrupo);
            ResultSet rsEst = estStmt.executeQuery();
            while (rsEst.next()) {
                idEstudiantes.add(rsEst.getInt("id_estudiante"));
            }

            grupos.add(new GrupoHorario(idGrupo, idMateria, idProfesor, cargaHoraria, idEstudiantes));
        }

        return grupos;
    }

    /** Carga la capacidad de las aulas */
    public List<Aula> cargarAulas(Connection conn) throws SQLException {
        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT id_aula, tipo_aula, capacidad, nombre FROM aulas";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                aulas.add(new Aula(
                    rs.getInt("id_aula"),
                    rs.getString("tipo_aula"),
                    rs.getInt("capacidad"),
                    rs.getString("nombre")
                ));
            }
        }
        return aulas;
    }

    /** Carga todas las materias con su tipo de aula y carga horaria */
    public Map<Integer, Materia> cargarMaterias(Connection conn) throws SQLException {
        Map<Integer, Materia> materias = new HashMap<>();
        String sql = "SELECT id_materia, nombre, carga_horaria, tipo_aula FROM materias";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                materias.put(
                    rs.getInt("id_materia"),
                    new Materia(
                        rs.getInt("id_materia"),
                        rs.getString("nombre"),
                        rs.getInt("carga_horaria"),
                        rs.getString("tipo_aula")
                    )
                );
            }
        }
        return materias;
    }

    /** Carga todos los profesores con sus preferencias */
    public Map<Integer, Profesor> cargarProfesores(Connection conn) throws SQLException {
        Map<Integer, Profesor> profesores = new HashMap<>();
        String sql = "SELECT id_profesor, nombre, correo, restriccion FROM profesores";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                profesores.put(
                    rs.getInt("id_profesor"),
                    new Profesor(
                        rs.getInt("id_profesor"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("restriccion")
                    )
                );
            }
        }
        return profesores;
    }

 /**
 * Genera automáticamente los horarios de clases eliminando los anteriores, 
 * asignando bloques disponibles a cada grupo según disponibilidad de profesores, 
 * estudiantes y aulas, y guardándolos en la base de datos.
 */
    public void generarHorarios() {
        try (Connection conn = Conexion.getConnection()) {
            // Esta función genera e inserta automáticamente los horarios de clases,
            // asegurándose de que no haya conflictos entre profesores, estudiantes ni aulas.

            // Paso 0: Limpiamos los horarios anteriores
            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM Horarios");
            deleteStmt.executeUpdate();
            deleteStmt.close();
            conn.setAutoCommit(false);

            // Paso 1: Verificamos que todos los estudiantes estén asignados a sus grupos
            asignarGruposFaltantes(conn);

            // Paso 2: Cargamos todos los datos necesarios del sistema
            List<Bloque> bloques = generarBloques();
            List<GrupoHorario> grupos = cargarGrupos(conn);
            List<Aula> aulas = cargarAulas(conn);
            Map<Integer, Materia> mapaMaterias = cargarMaterias(conn);
            Map<Integer, Profesor> mapaProfesores = cargarProfesores(conn);

            // Paso 3: Obtenemos la disponibilidad inicial de profesores, aulas y estudiantes
            Map<Integer, boolean[]> dispProfesores = Restricciones.obtenerDisponibilidadProfesores(conn);
            Map<Integer, boolean[]> dispAulas = Restricciones.obtenerDisponibilidadAulas(conn);
            Map<Integer, boolean[]> dispEstudiantes = Restricciones.obtenerDisponibilidadEstudiantes(conn);

            // Paso 4: Preparamos el INSERT para guardar los horarios en la base de datos
            String insertHorario = """
                INSERT INTO horarios (
                  id_materia,
                  id_profesor,
                  id_aula,
                  id_grupo,
                  dia_semana,
                  hora_inicio,
                  hora_fin
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
            PreparedStatement psInsert = conn.prepareStatement(insertHorario);

            // Paso 5: Intentamos asignar bloques a cada grupo de forma ordenada
            for (GrupoHorario g : grupos) {
                int idGrupo = g.getIdGrupo();
                Materia mat = mapaMaterias.get(g.getIdMateria());
                Profesor prof = mapaProfesores.get(g.getIdProfesor());
                int bloquesNecesarios = g.getBloquesNecesarios();
                List<Integer> idsEstudiantes = g.getEstudiantes();

                Set<Integer> diasOcupadosPorGrupo = new HashSet<>();
                List<int[]> asignacionesDeGrupo = new ArrayList<>();

                for (int bloqueId = 0; bloqueId < 36 && asignacionesDeGrupo.size() < bloquesNecesarios; bloqueId++) {
                    Bloque b = bloques.get(bloqueId);

                    if (diasOcupadosPorGrupo.contains(b.getDia())) continue;
                    if (dispProfesores.get(prof.getId())[bloqueId]) continue;

                    boolean cupoEstudiantesOk = true;
                    for (Integer idEst : idsEstudiantes) {
                        if (dispEstudiantes.get(idEst)[bloqueId]) {
                            cupoEstudiantesOk = false;
                            break;
                        }
                    }
                    if (!cupoEstudiantesOk) continue;

                    Integer aulaAsignada = null;
                    for (Aula aula : aulas) {
                        if (!aula.getTipoAula().equals(mat.getAula())) continue;
                        if (dispAulas.get(aula.getIdAula())[bloqueId]) continue;
                        aulaAsignada = aula.getIdAula();
                        break;
                    }
                    if (aulaAsignada == null) continue;

                    asignacionesDeGrupo.add(new int[]{bloqueId, aulaAsignada});
                    diasOcupadosPorGrupo.add(b.getDia());

                    dispProfesores.get(prof.getId())[bloqueId] = true;
                    dispAulas.get(aulaAsignada)[bloqueId] = true;
                    for (Integer idEst : idsEstudiantes) {
                        dispEstudiantes.get(idEst)[bloqueId] = true;
                    }
                }

                if (asignacionesDeGrupo.size() < bloquesNecesarios) {
                    JOptionPane.showMessageDialog(null,
                        "No se pudieron asignar todos los bloques para el grupo " + idGrupo +
                        " (necesita " + bloquesNecesarios + ")",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                }

                for (int[] par : asignacionesDeGrupo) {
                    int bloqueId = par[0];
                    int idAula = par[1];
                    Bloque b = bloques.get(bloqueId);

                    psInsert.setInt(1, mat.getId());
                    psInsert.setInt(2, prof.getId());
                    psInsert.setInt(3, idAula);
                    psInsert.setInt(4, idGrupo);

                    String[] diasTexto = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
                    psInsert.setString(5, diasTexto[b.getDia()]);
                    psInsert.setString(6, b.getHoraInicio() + ":00");
                    psInsert.setString(7, b.getHoraFin() + ":00");

                    psInsert.executeUpdate();
                }
            }

            conn.commit();
            JOptionPane.showMessageDialog(null,
                "Horarios generados e insertados correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Ocurrió un error al generar los horarios:\n" + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}