/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladoresconsultas;
import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.modelos.CarreraMateria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarreraMateriaManager {
    public void insertar(CarreraMateria cm) {
        String sql = "INSERT INTO carrera_materias (id_carrera, id_materia, semestre) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cm.getIdCarrera());
            ps.setInt(2, cm.getIdMateria());
            ps.setInt(3, cm.getSemestre());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int idCarrera, int idMateria) {
        String sql = "DELETE FROM carrera_materias WHERE id_carrera = ? AND id_materia = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCarrera);
            ps.setInt(2, idMateria);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CarreraMateria> obtenerTodas() {
        List<CarreraMateria> lista = new ArrayList<>();
        String sql = "SELECT * FROM carrera_materias";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idCarrera = rs.getInt("id_carrera");
                int idMateria = rs.getInt("id_materia");
                int semestre = rs.getInt("semestre");

                lista.add(new CarreraMateria(idCarrera, idMateria, semestre));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}



