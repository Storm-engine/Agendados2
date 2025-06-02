/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladoresconsultas;
import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.modelos.Carrera;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarreraManager {

    public void insertar(Carrera carrera) {
        String sql = "INSERT INTO carreras (nombre) VALUES (?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, carrera.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int idCarrera) {
        String sql = "DELETE FROM carreras WHERE id_carrera = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCarrera);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Carrera> obtenerTodas() {
        List<Carrera> lista = new ArrayList<>();
        String sql = "SELECT * FROM carreras";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idCarrera = rs.getInt("id_carrera");
                String nombre = rs.getString("nombre");
                lista.add(new Carrera(idCarrera, nombre));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public void actualizar(Carrera carrera) {
    String sql = "UPDATE carreras SET nombre = ? WHERE id_carrera = ?";

    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, carrera.getNombre());
        ps.setInt(2, carrera.getIdCarrera());
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}



