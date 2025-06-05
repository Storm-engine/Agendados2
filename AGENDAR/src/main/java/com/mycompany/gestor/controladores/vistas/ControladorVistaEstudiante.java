/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;

import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.controladoresconsultas.EstudianteManager;
import com.mycompany.gestor.modelos.Estudiante;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladorVistaEstudiante {
    EstudianteManager em = new EstudianteManager();

    public void insertar(int id, String nombre, String correo, int semestre, int id_carrera) {
        Estudiante estudiante = new Estudiante(id, nombre, correo, semestre, id_carrera);
        em.insertar(estudiante);
    }

    public void eliminar(int id) {
        em.eliminar(id);
    }

    // Esta es la parte importante para cargar los datos con nombre de carrera
    public void llenarTablaConConsulta(JTable tabla) {
        String sql = "SELECT e.id_estudiante, e.nombre, e.correo, e.semestre, c.nombre AS carrera " +
                     "FROM estudiantes e " +
                     "JOIN carreras c ON e.id_carrera = c.id_carrera";

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        
        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_estudiante");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                int semestre = rs.getInt("semestre");
                String carrera = rs.getString("carrera");

                modelo.addRow(new Object[]{id, nombre, correo, semestre, carrera});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
