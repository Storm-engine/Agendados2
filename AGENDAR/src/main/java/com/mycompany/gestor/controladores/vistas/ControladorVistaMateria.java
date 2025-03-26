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
    MateriaManager materiaManager = new MateriaManager();

    public List<Materia> consultar(JTable tabla) {
        List<Materia> materias = materiaManager.obtenerTodas();

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        for (Materia materia : materias) {
            modelo.addRow(new Object[]{
                materia.getId(),
                materia.getNombre(),
                materia.getCarga_horaria()
            });
        }

        return materias;
    }

    public void insertar(int idMateria, String nombre, int cargaHoraria) {
        Materia materia = new Materia(idMateria, nombre, cargaHoraria);
        materiaManager.insertar(materia);
    }

    public void eliminar(int idMateria) {
        materiaManager.eliminar(idMateria);
    }
}
