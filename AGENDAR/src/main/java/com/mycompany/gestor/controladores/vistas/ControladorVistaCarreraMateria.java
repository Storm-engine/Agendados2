/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;

import com.mycompany.gestor.controladoresconsultas.CarreraManager;
import com.mycompany.gestor.controladoresconsultas.CarreraMateriaManager;
import com.mycompany.gestor.controladoresconsultas.MateriaManager;
import com.mycompany.gestor.modelos.Carrera;
import com.mycompany.gestor.modelos.CarreraMateria;
import com.mycompany.gestor.modelos.Materia;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ControladorVistaCarreraMateria {
    CarreraMateriaManager manager = new CarreraMateriaManager();
    CarreraManager carreraManager = new CarreraManager();
    MateriaManager materiaManager = new MateriaManager();
    
    public void info_inicial(JComboBox box_carr, JComboBox box_mat) {
        box_carr.setModel(new DefaultComboBoxModel());
        box_mat.setModel(new DefaultComboBoxModel());

        List<Carrera> carreras = carreraManager.obtenerTodas();
        for (Carrera c : carreras) {
            box_carr.addItem(c.getIdCarrera());  // Solo agregas el nombre
        }
        List<Materia> materias = materiaManager.obtenerTodas();
        for (Materia m : materias) {
            box_mat.addItem(m.getId());  // Solo agregas el nombre
        }
    }
    public void insertar(int idCarrera, int idMateria, int semestre) {
        CarreraMateria cm = new CarreraMateria(idCarrera, idMateria, semestre);
        manager.insertar(cm);
    }

    public void eliminar(int idCarrera, int idMateria) {
        manager.eliminar(idCarrera, idMateria);
    }

    public List<CarreraMateria> consultar(JTable tabla) {
        List<CarreraMateria> lista = manager.obtenerTodas();

        MateriaManager materiaManager = new MateriaManager();
        CarreraManager carreraManager = new CarreraManager();

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        // Asegurarse de que las columnas estén en el orden correcto
        modelo.setColumnIdentifiers(new Object[]{"ID MATERIA", "Nombre Materia", "ID CARRERA", "Nombre Carrera", "Semestre"});

        // Cargar listas de referencia
        List<Materia> materias = materiaManager.obtenerTodas();
        List<Carrera> carreras = carreraManager.obtenerTodas();

        for (CarreraMateria cm : lista) {
            String nombreMateria = "Desconocido";
            String nombreCarrera = "Desconocido";

            // Buscar el nombre de la materia
            for (Materia m : materias) {
                if (m.getId() == cm.getIdMateria()) {
                    nombreMateria = m.getNombre();
                    break;
                }
            }

            // Buscar el nombre de la carrera
            for (Carrera c : carreras) {
                if (c.getIdCarrera() == cm.getIdCarrera()) {
                    nombreCarrera = c.getNombre();
                    break;
                }
            }

            modelo.addRow(new Object[]{
                cm.getIdMateria(),
                nombreMateria,
                cm.getIdCarrera(),
                nombreCarrera,
                cm.getSemestre()
            });
        }

        return lista;
    }
    
    public void actualizar(int id_materia,int id_carrera, int semestre){
        CarreraMateria new_cm = new CarreraMateria(id_materia,id_carrera, semestre);
        manager.actualizar(new_cm);
    }
    
}

