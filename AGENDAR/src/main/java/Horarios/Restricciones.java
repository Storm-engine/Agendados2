/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Horarios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sergio
 */
public class Restricciones {

    /**
     * Construye un Map<idProfesor, boolean[36]> que marca con true los bloques NO disponibles
     * según la restricción “mañana” / “tarde” / “ninguna”.
     */
    public static Map<Integer, boolean[]> obtenerDisponibilidadProfesores(Connection conn) throws SQLException {
        Map<Integer, boolean[]> disponibilidad = new HashMap<>();

        String sql = "SELECT id_profesor, nombre, restriccion FROM profesores";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_profesor");
                String restriccion = rs.getString("restriccion").toLowerCase().trim();
                // Creamos el arreglo de 36 bloques (todos inicialmente false = disponibles)
                boolean[] bloques = new boolean[36];

                for (int dia = 0; dia < 6; dia++) {
                    for (int hora = 0; hora < 6; hora++) {
                        int idx = dia * 6 + hora;
                        if (restriccion.equals("mañana") && hora >= 3) {
                            bloques[idx] = true; // Bloques de la tarde ocupados
                        } else if (restriccion.equals("tarde") && hora < 3) {
                            bloques[idx] = true; // Bloques de la mañana ocupados
                        }
                        // “ninguna” deja todo en false (disponible)
                    }
                }

                disponibilidad.put(id, bloques);
            }
        }

        return disponibilidad;
    }

    /**
     * Construye un Map<idAula, boolean[36]> marcando todos los bloques inicialmente como libres (false).
     * Más adelante, al asignar un grupo a un bloque, habrá que poner true en el bloque correspondiente.
     */
    public static Map<Integer, boolean[]> obtenerDisponibilidadAulas(Connection conn) throws SQLException {
        Map<Integer, boolean[]> disponibilidad = new HashMap<>();

        String sql = "SELECT id_aula FROM aulas";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idAula = rs.getInt("id_aula");
                disponibilidad.put(idAula, new boolean[36]);
                // Por defecto, todo queda false = disponible
            }
        }

        return disponibilidad;
    }

    /**
     * Construye un Map<idEstudiante, boolean[36]> marcando todos los bloques inicialmente como libres (false).
     * Cada vez que asignemos al estudiante una clase en un bloque, habrá que poner true en esa posición.
     */
    public static Map<Integer, boolean[]> obtenerDisponibilidadEstudiantes(Connection conn) throws SQLException {
        Map<Integer, boolean[]> disponibilidad = new HashMap<>();

        String sql = "SELECT id_estudiante FROM estudiantes";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idEst = rs.getInt("id_estudiante");
                disponibilidad.put(idEst, new boolean[36]);
            }
        }

        return disponibilidad;
    }

    /**
     * Optional: si alguna materia requiere tipo de aula concreto, 
     * podrías filtrar en GeneradorHorario para asignar solo las aulas cuyo tipo coincida con materia.tipoAula.
     * Por simplicidad, esta clase no lo contempla aquí.
     */
}
