/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;

import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.controladoresconsultas.CarreraMateriaManager;
import com.mycompany.gestor.controladoresconsultas.EstudianteManager;
import com.mycompany.gestor.modelos.CarreraMateria;
import com.mycompany.gestor.modelos.Estudiante;
import java.sql.*;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladorVistaCarreraMateria {
    CarreraMateriaManager manager = new CarreraMateriaManager();

    public void insertar(int idCarrera, int idMateria, int semestre) {
        CarreraMateria cm = new CarreraMateria(idCarrera, idMateria, semestre);
        manager.insertar(cm);
    }

    public void eliminar(int idCarrera, int idMateria) {
        manager.eliminar(idCarrera, idMateria);
    }

    public List<CarreraMateria> consultar(JTable tabla) {
        List<CarreraMateria> lista = manager.obtenerTodas();
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        for (CarreraMateria cm : lista) {
            modelo.addRow(new Object[]{
                cm.getIdCarrera(),
                cm.getIdMateria(),
                cm.getSemestre()
            });
        }

        return lista;
    }
}

