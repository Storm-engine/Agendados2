/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladoresconsultas;

/**
 *
 * @author Sergio
 */
import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.modelos.Grupo;
import com.mycompany.gestor.modelos.Materia;
import com.mycompany.gestor.modelos.Profesor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoManager {

    public List<Grupo> obtenerTodos() {
        List<Grupo> grupos = new ArrayList<>();
        String sql = "SELECT id_grupo, id_materia, id_profesor FROM grupos";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Grupo grupo = new Grupo(
                    rs.getInt("id_grupo"),
                    rs.getInt("id_materia"),
                    rs.getInt("id_profesor")
                );
                grupos.add(grupo);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return grupos;
    }

    public void insertar(Grupo grupo) {
        String sql = "INSERT INTO grupos (id_materia, id_profesor) VALUES (?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, grupo.getIdMateria());
            ps.setInt(2, grupo.getIdProfesor());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminar(int idGrupo) {
        String sql = "DELETE FROM grupos WHERE id_grupo = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar(Grupo grupo) {
        String sql = "UPDATE grupos SET id_materia = ?, id_profesor = ? WHERE id_grupo = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, grupo.getIdMateria());
            ps.setInt(2, grupo.getIdProfesor());
            ps.setInt(3, grupo.getIdGrupo());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

