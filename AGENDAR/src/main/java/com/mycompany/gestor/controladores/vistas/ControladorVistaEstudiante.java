/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;

import com.mycompany.gestor.controladoresconsultas.EstudianteManager;
import com.mycompany.gestor.modelos.Estudiante;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sergio David
 */
public class ControladorVistaEstudiante {
    EstudianteManager em = new EstudianteManager();
    public List<Estudiante> consultar(JTable tbl) {
        List<Estudiante> estudiantes = em.obtenerTodos();

        DefaultTableModel modelo = (DefaultTableModel) tbl.getModel();

        modelo.setRowCount(0);

        for (Estudiante est : estudiantes) {
        modelo.addRow(new Object[]{
            est.getId(), 
            est.getNombre(), 
            est.getCorreo(), 
            est.getSemestre()
        });
        }

        return estudiantes;
        }

    public void insertar(int id, String nombre, String correo, int semestre){
        Estudiante estudiante = new Estudiante( id,  nombre,  correo,  semestre);
        em.insertar(estudiante);
    }
    
    public void eliminar(int id){
        em.eliminar(id);
    }
}
