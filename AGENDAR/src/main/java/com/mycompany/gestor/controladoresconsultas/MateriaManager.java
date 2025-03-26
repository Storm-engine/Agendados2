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


public class MateriaManager {
    public List<Materia> obtenerTodos() {
        List<Materia> materias = new ArrayList<>();

        String sql = "SELECT * FROM materias";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_materia");
                String nombre = rs.getString("nombre");
                int carga_horaria = rs.getInt("carga_horaria");

                Materia m = new Materia(id, nombre, carga_horaria);
                materias.add(m);
                m.toString();
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // o tu propio manejo de errores
        }

        return materias;
    }

    // Método para insertar un nuevo estudiante
    public void insertar(Materia materia) {
        String sql = "INSERT INTO materias (id_materia, nombre, carga_horaria) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, materia.getId());
            ps.setString(2, materia.getNombre());
            ps.setInt(3, materia.getCarga_horaria());

            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Puedes hacer métodos para actualizar y eliminar tambien
}
