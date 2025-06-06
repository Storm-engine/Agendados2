/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;

import com.mycompany.gestor.controladoresconsultas.CarreraManager;
import com.mycompany.gestor.modelos.Carrera;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ControladorVistaCarrera {
    private CarreraManager manager = new CarreraManager();

    public void insertar(String nombre) {
        Carrera carrera = new Carrera(0, nombre); // El ID es autoincremental
        manager.insertar(carrera);
    }

    public void eliminar(int idCarrera) {
        manager.eliminar(idCarrera);
    }

    public List<Carrera> consultar(JTable tabla) {
        List<Carrera> lista = manager.obtenerTodas();
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        for (Carrera carrera : lista) {
            modelo.addRow(new Object[]{
                carrera.getIdCarrera(),
                carrera.getNombre()
            });
        }

        return lista;
    }
    
    public void actualizar(int idCarrera, String nuevoNombre) {
    Carrera carrera = new Carrera(idCarrera, nuevoNombre);
    manager.actualizar(carrera);
}
}
