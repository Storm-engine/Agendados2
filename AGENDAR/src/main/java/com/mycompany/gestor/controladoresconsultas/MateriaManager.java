/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladoresconsultas;
import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.modelos.Materia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaManager {
    public List<Materia> obtenerTodas() {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM materias";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                materias.add(new Materia(rs.getInt("id_materia"), rs.getString("nombre"), rs.getInt("carga_horaria")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return materias;
    }

    public void insertar(Materia materia) {
        String sql = "INSERT INTO materias (nombre, carga_horaria) VALUES (?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getCarga_horaria());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar(Materia materia) {
        String sql = "UPDATE materias SET nombre = ?, carga_horaria = ? WHERE id_materia = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getCarga_horaria());
            ps.setInt(3, materia.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM materias WHERE id_materia = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
