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
        String sql = "SELECT grupos.id_grupo, materias.id_materia, materias.nombre, materias.carga_horaria, " +
                     "profesores.id_profesor, profesores.nombre, profesores.correo, profesores.restriccion_horaria " +
                     "FROM grupos " +
                     "JOIN materias ON grupos.id_materia = materias.id_materia " +
                     "JOIN profesores ON grupos.id_profesor = profesores.id_profesor";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idGrupo = rs.getInt("id_grupo");
                int idMateria = rs.getInt("id_materia");
                String nombreMateria = rs.getString("nombre");
                int cargaHoraria = rs.getInt("carga_horaria");
                Materia materia = new Materia(idMateria, nombreMateria, cargaHoraria);
                int idProfesor = rs.getInt("id_profesor");
                String nombreProfesor = rs.getString("profesores.nombre");
                String correo = rs.getString("correo");
                Profesor.restriccion_horaria restriccion = Profesor.restriccion_horaria.valueOf(rs.getString("restriccion_horaria").toUpperCase());
                Profesor profesor = new Profesor(idProfesor, nombreProfesor, correo, restriccion);

                grupos.add(new Grupo(idGrupo, materia, profesor));
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

            ps.setInt(1, grupo.getMateria().getId());
            ps.setInt(2, grupo.getProfesor().getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar(Grupo grupo) {
        String sql = "UPDATE grupos SET id_materia = ?, id_profesor = ? WHERE id_grupo = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, grupo.getMateria().getId());
            ps.setInt(2, grupo.getProfesor().getId());
            ps.setInt(3, grupo.getIdGrupo());
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
}
