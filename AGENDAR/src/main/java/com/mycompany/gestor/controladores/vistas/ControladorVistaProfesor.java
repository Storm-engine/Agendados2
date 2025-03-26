/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;

import com.mycompany.gestor.controladoresconsultas.ProfesorManager;
import com.mycompany.gestor.modelos.Profesor;

/**
 *
 * @author USER
 */
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ControladorVistaProfesor {
    ProfesorManager profesorManager = new ProfesorManager();

    public List<Profesor> consultar(JTable tabla) {
        List<Profesor> profesores = profesorManager.obtenerTodos();

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        for (Profesor profesor : profesores) {
            modelo.addRow(new Object[]{
                profesor.getId(),
                profesor.getNombre(),
                profesor.getCorreo(),
                profesor.getRestriccionHoraria()
            });
        }

        return profesores;
    }

    public void insertar(int id, String nombre, String correo, Profesor.restriccion_horaria restriccionHoraria) {
        Profesor profesor = new Profesor(id, nombre, correo, restriccionHoraria);
        profesorManager.insertar(profesor);
    }

    public void eliminar(int id) {
        profesorManager.eliminar(id);
    }
}
