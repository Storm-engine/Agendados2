/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;

import com.mycompany.gestor.controladoresconsultas.ProfesorManager;
import com.mycompany.gestor.modelos.Profesor;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladorVistaProfesor {
    ProfesorManager pm = new ProfesorManager();

    public List<Profesor> consultar(JTable tbl) {
        List<Profesor> profesores = pm.obtenerTodos();
        DefaultTableModel modelo = (DefaultTableModel) tbl.getModel();
        modelo.setRowCount(0);

        for (Profesor prof : profesores) {
            modelo.addRow(new Object[]{
                prof.getId(),
                prof.getNombre(),
                prof.getCorreo(),
                prof.getRestriccionHoraria().toString()
            });
        }

        return profesores;
    }

    public void insertar(int id, String nombre, String correo, Profesor.restriccion_horaria restriccion) {
        Profesor profesor = new Profesor(id, nombre, correo, restriccion);
        pm.insertar(profesor);
    }

    public void eliminar(int id) {
        pm.eliminar(id);
    }

    public void actualizar(int id, String nombre, String correo, Profesor.restriccion_horaria restriccion) {
        Profesor profesor = new Profesor(id, nombre, correo, restriccion);
        pm.actualizar(profesor);
    }
}
