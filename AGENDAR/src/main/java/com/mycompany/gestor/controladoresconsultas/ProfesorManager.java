/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladoresconsultas;

import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.modelos.Profesor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorManager {
    public List<Profesor> obtenerTodos() {
        List<Profesor> profesores = new ArrayList<>();

        String sql = "select * from profesores";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_profesor");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String restriccionStr = rs.getString("restriccion");

                Profesor.restriccion_horaria restriccion = 
                    Profesor.restriccion_horaria.valueOf(restriccionStr.toUpperCase());

                Profesor p = new Profesor(id, nombre, correo, restriccion);
                profesores.add(p);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return profesores;
    }

    public void insertar(Profesor profesor) {
        String sql = "insert into profesores (id_profesor, nombre, correo, restriccion) values (?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, profesor.getId());
            ps.setString(2, profesor.getNombre());
            ps.setString(3, profesor.getCorreo());
            ps.setString(4, profesor.getRestriccionHoraria().toString());

            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar(Profesor profesor) {
        String sql = "UPDATE profesores SET nombre = ?, correo = ?, restriccion = ? WHERE id_profesor = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getCorreo());
            ps.setString(3, profesor.getRestriccionHoraria().toString());
            ps.setInt(4, profesor.getId());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("profesor actualizado correctamente");
            } else {
                System.out.println("No se encontro el profesor con ID: " + profesor.getId());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminar(int id){
        String sql = "delete from profesores where id_profesor = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
