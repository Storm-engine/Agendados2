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

                Profesor.restriccion_horaria restriccion = Profesor.restriccion_horaria.valueOf(restriccion_horaria.toUpperCase());
                Profesor p = new Profesor(id, nombre, correo, restriccion);
                profesores.add(p);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // o tu propio manejo de errores
        }

        return profesores;
    }

    // Método para insertar un nuevo estudiante
    public void insertar(Profesor profesor) {
        String sql = "INSERT INTO profesores (id_profesor, nombre, correo, restriccion_horaria) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, profesor.getId());
            ps.setString(2, profesor.getNombre());
            ps.setString(3, profesor.getCorreo());
            ps.setString(4, profesor.getRestriccionHoraria().name());

            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
