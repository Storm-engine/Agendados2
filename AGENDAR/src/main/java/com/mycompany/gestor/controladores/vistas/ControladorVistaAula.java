/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;

/**
 *
 * @author Sergio
 */
import com.mycompany.gestor.controladoresconsultas.AulaManager;
import com.mycompany.gestor.modelos.Aula;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ControladorVistaAula {
    AulaManager aulaManager = new AulaManager();

    public List<Aula> consultar(JTable tabla) {
        List<Aula> aulas = aulaManager.obtenerTodas();

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        for (Aula aula : aulas) {
            modelo.addRow(new Object[]{
                aula.getIdAula(),
                aula.getTipoAula(),
                aula.getCapacidad()
            });
        }

        return aulas;
    }

    public void insertar(int idAula, Aula.TipoAula tipoAula, int capacidad) {
        Aula aula = new Aula(idAula, tipoAula, capacidad);
        aulaManager.insertar(aula);
    }

    public void eliminar(int idAula) {
        aulaManager.eliminar(idAula);
    }
}
