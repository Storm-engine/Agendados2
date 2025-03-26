/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladoresconsultas;

import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.modelos.Aula;
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
public class AulaManager {
    public List<Aula> obtenerTodas() {
        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT * FROM aulas";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_aula");
                Aula.TipoAula tipo = Aula.TipoAula.valueOf(rs.getString("tipo_aula").toUpperCase());
                int capacidad = rs.getInt("capacidad");

                aulas.add(new Aula(id, tipo, capacidad));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return aulas;
    }

    public void insertar(Aula aula) {
        String sql = "INSERT INTO aulas (tipo_aula, capacidad) VALUES (?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, aula.getTipoAula().name().toLowerCase());
            ps.setInt(2, aula.getCapacidad());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar(Aula aula) {
        String sql = "UPDATE aulas SET tipo_aula = ?, capacidad = ? WHERE id_aula = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, aula.getTipoAula().name().toLowerCase());
            ps.setInt(2, aula.getCapacidad());
            ps.setInt(3, aula.getIdAula());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM aulas WHERE id_aula = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}