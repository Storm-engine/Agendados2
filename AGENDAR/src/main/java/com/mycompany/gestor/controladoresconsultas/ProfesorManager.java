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
                Profesor.restriccion_horaria restriccion = Profesor.restriccion_horaria.valueOf(rs.getString("restriccion_horaria").toUpperCase());

                profesores.add(new Profesor(id, nombre, correo, restriccion));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return profesores;
    }

    public void insertar(Profesor profesor) {
        String sql = "INSERT INTO profesores (nombre, correo, restriccion_horaria) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getCorreo());
            ps.setString(3, profesor.getRestriccionHoraria().name().toLowerCase());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar(Profesor profesor) {
        String sql = "UPDATE profesores SET nombre = ?, correo = ?, restriccion_horaria = ? WHERE id_profesor = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getCorreo());
            ps.setString(3, profesor.getRestriccionHoraria().name().toLowerCase());
            ps.setInt(4, profesor.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM profesores WHERE id_profesor = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}