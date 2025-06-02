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
                Materia m = new Materia(
                    rs.getInt("id_materia"),
                    rs.getString("nombre"),
                    rs.getInt("carga_horaria"),
                    rs.getString("tipo_aula")
                );
                materias.add(m);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return materias;
    }

    public void insertar(Materia m) {
        String sql = "INSERT INTO materias (nombre, carga_horaria, tipo_aula) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getCarga_horaria());
            ps.setString(3, m.getAula());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar(Materia m) {
        String sql = "UPDATE materias SET nombre = ?, carga_horaria = ?, tipo_aula = ? WHERE id_materia = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getCarga_horaria());
            ps.setString(3, m.getAula());
            ps.setInt(4, m.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminar(int id_materia) {
        String sql = "DELETE FROM materias WHERE id_materia = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_materia);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}


