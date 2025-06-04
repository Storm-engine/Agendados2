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
        sql = "SELECT horarios.*, estudiante_grupos.id_estudiante " +
              "FROM horarios " +
              "INNER JOIN estudiante_grupos ON estudiante_grupos.id_grupo = horarios.id_grupo " +
              "WHERE estudiante_grupos.id_estudiante = ?";
    } else if (categoria.equals("Profesor")) {
        sql = "SELECT * FROM horarios WHERE horarios.id_profesor = ?";
    } else if (categoria.equals("Aula")) {
        sql = "SELECT * FROM horarios WHERE horarios.id_aula = ?";
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
                rs.getInt("id_materia"),
                rs.getInt("id_profesor"),
                rs.getInt("id_aula"),
                rs.getInt("id_grupo"),
                rs.getString("dia_semana"),
                rs.getString("hora_inicio"),
                rs.getString("hora_fin"),
                categoria.equals("Estudiante") ? rs.getInt("id_estudiante") : null,
                rs.getInt("id_grupo") 
            });
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

}

