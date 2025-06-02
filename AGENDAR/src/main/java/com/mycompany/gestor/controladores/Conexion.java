/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores;

import com.mycompany.gestor.modelos.GrupoHorario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Conexion {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:sqlite:db/mi_base.db");
        }
        
        return connection;
    }
    
    public List<GrupoHorario> cargarGrupos(Connection conn) throws SQLException {
    List<GrupoHorario> grupos = new ArrayList<>();

    String query = "SELECT g.id_grupo, g.id_materia, g.id_profesor, m.carga_horaria " +
                   "FROM grupos g JOIN materias m ON m.id_materia = g.id_materia";
    PreparedStatement stmt = conn.prepareStatement(query);
    ResultSet rs = stmt.executeQuery();

    while (rs.next()) {
        int idGrupo = rs.getInt("id_grupo");
        int idMateria = rs.getInt("id_materia");
        int idProfesor = rs.getInt("id_profesor");
        int cargaHoraria = rs.getInt("carga_horaria");

        // Cargar estudiantes
        List<Integer> idEstudiantes = new ArrayList<>();
        PreparedStatement estudiantesStmt = conn.prepareStatement(
            "SELECT id_estudiante FROM estudiante_grupos WHERE id_grupo = ?"
        );
        estudiantesStmt.setInt(1, idGrupo);
        ResultSet rsEstudiantes = estudiantesStmt.executeQuery();
        while (rsEstudiantes.next()) {
            idEstudiantes.add(rsEstudiantes.getInt("id_estudiante"));
        }

        grupos.add(new GrupoHorario(idGrupo, idMateria, idProfesor, cargaHoraria, idEstudiantes));
    }

    return grupos;
}

}