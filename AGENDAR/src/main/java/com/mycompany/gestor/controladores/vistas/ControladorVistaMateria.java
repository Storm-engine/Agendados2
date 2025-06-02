/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;


import com.mycompany.gestor.controladoresconsultas.MateriaManager;
import com.mycompany.gestor.modelos.Materia;

/**
 *
 * @author Sergio David
 */
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ControladorVistaMateria {
    MateriaManager mm = new MateriaManager();

    public List<Materia> consultar(JTable tabla) {
        List<Materia> materias = mm.obtenerTodas();
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        for (Materia m : materias) {
            modelo.addRow(new Object[]{
                m.getId(),
                m.getNombre(),
                m.getCarga_horaria(),
                m.getAula()
            });
        }

        return materias;
    }

    public void insertar(String nombre, int carga_horaria, String aula) {
        Materia m = new Materia(0, nombre, carga_horaria, aula); // ID será autogenerado
        mm.insertar(m);
    }

    public void actualizar(int id, String nombre, int carga_horaria, String aula) {
        Materia m = new Materia(id, nombre, carga_horaria, aula);
        mm.actualizar(m);
    }

    public void eliminar(int id) {
        mm.eliminar(id);
    }
}

