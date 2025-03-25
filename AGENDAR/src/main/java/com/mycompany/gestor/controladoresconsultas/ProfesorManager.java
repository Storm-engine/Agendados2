/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladoresconsultas;

import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.modelos.Profesor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class ProfesorManager {
    public List<Profesor> obtenerTodos() {
        List<Profesor> profesores = new ArrayList<>();

        String sql = "SELECT * FROM profesores";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_profesor");
                
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String restriccion_horaria = rs.getString("restriccion_horaria");

                Profesor p = new Profesor(id, nombre, correo, restriccion_horaria);
                estudiantes.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // o tu propio manejo de errores
        }

        return estudiantes;
    }

    // Método para insertar un nuevo estudiante
    public void insertar(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (id_estudiante, nombre, correo, semestre) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, estudiante.getId());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getCorreo());
            ps.setInt(4, estudiante.getSemestre());

            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
