/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladoresconsultas;
import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.modelos.Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EstudianteManager {
    public List<Estudiante> obtenerTodos() {
        List<Estudiante> estudiantes = new ArrayList<>();

        String sql = "SELECT * FROM estudiantes";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_estudiante");
                
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                int semestre = rs.getInt("semestre");

                Estudiante e = new Estudiante(id, nombre, correo, semestre);
                estudiantes.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // o tu propio manejo de errores
        }

        return estudiantes;
    }

    
    public void actualizar(Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET nombre = ?, correo = ?, semestre = ? WHERE id_estudiante = ?";

        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getCorreo());
            ps.setInt(3, estudiante.getSemestre());
            ps.setInt(4, estudiante.getId());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Estudiante actualizado correctamente.");
            } else {
                System.out.println("No se encontró el estudiante con ID: " + estudiante.getId());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
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
    
    public void eliminar(int id){
        String sql = "delete from estudiantes where id_estudiante = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
