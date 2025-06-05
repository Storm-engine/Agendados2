/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;
import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.modelos.Horario;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author USER
 */

public class ControladorVistaHorario {
public void llenarHorario(JTable tabla, int id, String categoria) {
    String sql = "";

    if (categoria.equals("Estudiante")) {
        sql = "SELECT horarios.id_horario, materias.nombre AS materia, profesores.nombre AS profesor, " +
              "aulas.nombre AS aula, horarios.id_grupo, horarios.dia_semana, horarios.hora_inicio, horarios.hora_fin, " +
              "estudiantes.nombre AS estudiante " +
              "FROM horarios " +
              "INNER JOIN materias ON horarios.id_materia = materias.id_materia " +
              "INNER JOIN profesores ON horarios.id_profesor = profesores.id_profesor " +
              "INNER JOIN aulas ON horarios.id_aula = aulas.id_aula " +
              "INNER JOIN grupos ON horarios.id_grupo = grupos.id_grupo " +
              "INNER JOIN estudiante_grupos ON grupos.id_grupo = estudiante_grupos.id_grupo " +
              "INNER JOIN estudiantes ON estudiante_grupos.id_estudiante = estudiantes.id_estudiante " +
              "WHERE estudiantes.id_estudiante = ?";
    } else if (categoria.equals("Profesor")) {
        sql = "SELECT horarios.id_horario, materias.nombre AS materia, profesores.nombre AS profesor, " +
              "aulas.nombre AS aula, horarios.id_grupo, horarios.dia_semana, horarios.hora_inicio, horarios.hora_fin " +
              "FROM horarios " +
              "INNER JOIN materias ON horarios.id_materia = materias.id_materia " +
              "INNER JOIN profesores ON horarios.id_profesor = profesores.id_profesor " +
              "INNER JOIN aulas ON horarios.id_aula = aulas.id_aula " +
              "WHERE profesores.id_profesor = ?";
    } else if (categoria.equals("Aula")) {
        sql = "SELECT horarios.id_horario, materias.nombre AS materia, profesores.nombre AS profesor, " +
              "aulas.nombre AS aula, horarios.id_grupo, horarios.dia_semana, horarios.hora_inicio, horarios.hora_fin " +
              "FROM horarios " +
              "INNER JOIN materias ON horarios.id_materia = materias.id_materia " +
              "INNER JOIN profesores ON horarios.id_profesor = profesores.id_profesor " +
              "INNER JOIN aulas ON horarios.id_aula = aulas.id_aula " +
              "WHERE aulas.id_aula = ?";
    }

    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0);

    try (Connection con = Conexion.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getInt("id_horario"),
                rs.getString("materia"),
                rs.getString("profesor"),
                rs.getString("aula"),
                rs.getInt("id_grupo"),
                rs.getString("dia_semana"),
                rs.getString("hora_inicio"),
                rs.getString("hora_fin"),
                categoria.equals("Estudiante") ? rs.getString("estudiante") : null,
                
            });
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

}

