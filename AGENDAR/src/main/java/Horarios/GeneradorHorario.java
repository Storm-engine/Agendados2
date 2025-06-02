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
import java.util.List;
import java.util.Map;

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

    /**
     * Asigna automáticamente a cada estudiante los grupos que le falten,
     * según carrera y semestre.
     * (Este método ya lo tenías implementado).
     */
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

                // Elegimos el grupo con menos inscritos (o el primero si están iguales)
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
                    // Insertar estudiante en ese grupo
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
                    // Podrías decidir crear un grupo nuevo aquí si lo prefieres
                }
            }
        }
    }

    /**
     * Carga todos los grupos existentes con su materia, profesor, carga horaria y lista de estudiantes.
     */
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

            // Cargar lista de estudiantes del grupo
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

    /**
     * Carga todas las aulas con su tipo y capacidad.
     */
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

    /**
     * Carga todas las materias con su tipo de aula (nuevo campo) y carga horaria.
     */
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

    /**
     * Carga todos los profesores con su restricción horaria (“mañana”, “tarde”, “ninguna”).
     */
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
     * Arma el horario listando, para cada grupo, cuáles bloques y qué aula ocupar.
     * Luego inserta esas filas en la tabla “horarios”.
     */
    public void generarHorarios() {
        try (Connection conn = Conexion.getConnection()) {
            conn.setAutoCommit(false);

            // 1) Primero, asignamos grupos faltantes a estudiantes:
            asignarGruposFaltantes(conn);

            // 2) Cargar estructuras básicas
            List<Bloque> bloques = generarBloques();
            List<GrupoHorario> grupos = cargarGrupos(conn);
            List<Aula> aulas = cargarAulas(conn);
            Map<Integer, Materia> mapaMaterias = cargarMaterias(conn);
            Map<Integer, Profesor> mapaProfesores = cargarProfesores(conn);

            // 3) Disponibilidades iniciales (todo false = libre)
            Map<Integer, boolean[]> dispProfesores = Restricciones.obtenerDisponibilidadProfesores(conn);
            Map<Integer, boolean[]> dispAulas = Restricciones.obtenerDisponibilidadAulas(conn);
            Map<Integer, boolean[]> dispEstudiantes = Restricciones.obtenerDisponibilidadEstudiantes(conn);

            // 4) Preparamos el INSERT en la tabla “horarios”
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

            // 5) Algoritmo de asignación (greedy simple)
            for (GrupoHorario g : grupos) {
                int idGrupo = g.getIdGrupo();
                Materia mat = mapaMaterias.get(g.getIdMateria());
                Profesor prof = mapaProfesores.get(g.getIdProfesor());
                int bloquesNecesarios = g.getBloquesNecesarios();
                List<Integer> idsEstudiantes = g.getEstudiantes();

                // Lista para guardar los pares (bloqueId, idAula) que vamos a asignar
                List<int[]> asignacionesDeGrupo = new ArrayList<>();

                // Intentamos asignar X bloques distintos
                for (int bloqueId = 0; bloqueId < 36 && asignacionesDeGrupo.size() < bloquesNecesarios; bloqueId++) {
                    // 5.1) Verificar disponibilidad profesor en ese bloque
                    if (dispProfesores.get(prof.getId())[bloqueId]) continue;

                    // 5.2) Verificar disponibilidad de TODOS los estudiantes
                    boolean cupoEstudiantesOk = true;
                    for (Integer idEst : idsEstudiantes) {
                        if (dispEstudiantes.get(idEst)[bloqueId]) {
                            cupoEstudiantesOk = false;
                            break;
                        }
                    }
                    if (!cupoEstudiantesOk) continue;

                    // 5.3) Buscar un aula válida y libre en ese bloque:
                    Integer aulaAsignada = null;
                    for (Aula aula : aulas) {
                        // a) Tipo de aula debe coincidir con el tipo que requiere la materia
                        if (!aula.getTipoAula().equals(mat.getAula())) continue;
                        // b) Aula debe estar libre en este bloque
                        if (dispAulas.get(aula.getIdAula())[bloqueId]) continue;
                        // c) (Opcional) Verificar capacidad: 
                        //    Si quisieras que no se sobrepase la capacidad, tendrías que contar inscritos.
                        aulaAsignada = aula.getIdAula();
                        break;
                    }
                    if (aulaAsignada == null) {
                        // No hay aula libre/tipo compatible en este bloque
                        continue;
                    }

                    // 5.4) Si llego acá, el profesor y los estudiantes están libres y hay aula válida
                    asignacionesDeGrupo.add(new int[]{bloqueId, aulaAsignada});

                    // 5.5) Marcar bloque como ocupado en profesor, aula y estudiantes
                    dispProfesores.get(prof.getId())[bloqueId] = true;
                    dispAulas.get(aulaAsignada)[bloqueId] = true;
                    for (Integer idEst : idsEstudiantes) {
                        dispEstudiantes.get(idEst)[bloqueId] = true;
                    }
                }

                // Si no encontramos suficientes bloques, registramos un warning
                if (asignacionesDeGrupo.size() < bloquesNecesarios) {
                    System.out.println("⚠ No se pudieron asignar todos los bloques para el grupo " + idGrupo
                                       + " (necesita " + bloquesNecesarios + ")");
                }

                // 6) Insertar en BD todos los bloques asignados para este grupo
                for (int[] par : asignacionesDeGrupo) {
                    int bloqueId = par[0];
                    int idAula   = par[1];
                    Bloque b = bloques.get(bloqueId);

                    psInsert.setInt(1, mat.getId());
                    psInsert.setInt(2, prof.getId());
                    psInsert.setInt(3, idAula);
                    psInsert.setInt(4, idGrupo);
                    // dia_semana como texto
                    String[] diasTexto = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
                    psInsert.setString(5, diasTexto[b.getDia()]);
                    // hora_inicio = “6:00”, “8:00”, etc.
                    psInsert.setString(6, b.getHoraInicio() + ":00");
                    psInsert.setString(7, b.getHoraFin() + ":00");

                    psInsert.executeUpdate();
                }
            }

            conn.commit();
            System.out.println("✅ Horarios generados e insertados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}