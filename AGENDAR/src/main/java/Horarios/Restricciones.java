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
     * según la restricción “mañana” / “tarde” / “ninguna” (campo “restriccion” en la tabla).
     */
    public static Map<Integer, boolean[]> obtenerDisponibilidadProfesores(Connection conn) throws SQLException {
        Map<Integer, boolean[]> disponibilidad = new HashMap<>();

        String sql = "SELECT id_profesor, restriccion FROM profesores";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_profesor");
                String restriccion = rs.getString("restriccion").toLowerCase().trim();
                boolean[] bloques = new boolean[36]; // false = disponible

                for (int dia = 0; dia < 6; dia++) {
                    for (int hora = 0; hora < 6; hora++) {
                        int idx = dia * 6 + hora;
                        if (restriccion.equals("mañana") && hora >= 3) {
                            bloques[idx] = true; // solo trabaja en la mañana
                        } else if (restriccion.equals("tarde") && hora < 3 && hora >= 5) {
                            bloques[idx] = true; // solo trabaja en la tarde
                        }
                        else if (restriccion.equals("noche") && hora <= 5) {
                            bloques[idx] = true; // solo trabaja en la noche
                        }
                        // “ninguna” → todos false
                    }
                }

                disponibilidad.put(id, bloques);
            }
        }

        return disponibilidad;
    }

    /**
     * Construye un Map<idAula, boolean[36]> marcando todos los bloques inicialmente como libres (false).
     */
    public static Map<Integer, boolean[]> obtenerDisponibilidadAulas(Connection conn) throws SQLException {
        Map<Integer, boolean[]> disponibilidad = new HashMap<>();

        String sql = "SELECT id_aula FROM aulas";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idAula = rs.getInt("id_aula");
                disponibilidad.put(idAula, new boolean[36]);
            }
        }

        return disponibilidad;
    }

    /**
     * Construye un Map<idEstudiante, boolean[36]> marcando todos los bloques inicialmente como libres (false).
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
}
