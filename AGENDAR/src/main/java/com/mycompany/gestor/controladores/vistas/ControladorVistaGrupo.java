/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;

/**
 *
 * @author Sergio
 */
import com.mycompany.gestor.controladoresconsultas.GrupoManager;
import com.mycompany.gestor.controladoresconsultas.MateriaManager;
import com.mycompany.gestor.controladoresconsultas.ProfesorManager;
import com.mycompany.gestor.modelos.Grupo;
import com.mycompany.gestor.modelos.Materia;
import com.mycompany.gestor.modelos.Profesor;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ControladorVistaGrupo {
    private final GrupoManager manager = new GrupoManager();
    private final MateriaManager materiaManager = new MateriaManager();
    private final ProfesorManager profesorManager = new ProfesorManager();

    public void cargarComboMaterias(JComboBox combo) {
        combo.removeAllItems();
        for (Materia m : materiaManager.obtenerTodas()) {
            combo.addItem(m.getId()); // solo ID, como trabajas internamente
        }
    }

    public void cargarComboProfesores(JComboBox combo) {
        combo.removeAllItems();
        for (Profesor p : profesorManager.obtenerTodos()) {
            combo.addItem(p.getId()); // solo ID
        }
    }

    public void insertar(int idMateria, int idProfesor) {
        Grupo grupo = new Grupo(0, idMateria, idProfesor); // idGrupo será ignorado por autoincrement
        manager.insertar(grupo);
    }

    public void eliminar(int idGrupo) {
        manager.eliminar(idGrupo);
    }

    public void actualizar(int idGrupo, int idMateria, int idProfesor) {
        Grupo grupo = new Grupo(idGrupo, idMateria, idProfesor);
        manager.actualizar(grupo);
    }

    public List<Grupo> consultar(JTable tabla) {
        List<Grupo> grupos = manager.obtenerTodos();
        List<Materia> materias = materiaManager.obtenerTodas();
        List<Profesor> profesores = profesorManager.obtenerTodos();

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new Object[]{
            "ID GRUPO", "ID MATERIA", "Nombre Materia",
            "ID PROFESOR", "Nombre Profesor"
        });

        for (Grupo g : grupos) {
            String nombreMateria = "Desconocida";
            String nombreProfesor = "Desconocido";
            String correo = "";
            String restriccion = "";

            for (Materia m : materias) {
                if (m.getId() == g.getIdMateria()) {
                    nombreMateria = m.getNombre();
                    break;
                }
            }

            for (Profesor p : profesores) {
                if (p.getId() == g.getIdProfesor()) {
                    nombreProfesor = p.getNombre();
                    correo = p.getCorreo();
                    restriccion = p.getRestriccionHoraria().toString();
                    break;
                }
            }

            modelo.addRow(new Object[]{
                g.getIdGrupo(),
                g.getIdMateria(),
                nombreMateria,
                g.getIdProfesor(),
                nombreProfesor
            });
        }

        return grupos;
    }
    
    public void info_inicial(JComboBox box_materia, JComboBox box_profesor) {
        box_materia.setModel(new DefaultComboBoxModel());
        box_profesor.setModel(new DefaultComboBoxModel());

        MateriaManager materiaManager = new MateriaManager();
        ProfesorManager profesorManager = new ProfesorManager();

        List<Materia> materias = materiaManager.obtenerTodas();
        for (Materia m : materias) {
            box_materia.addItem(m.getId());  // Solo agregas el ID
        }

        List<Profesor> profesores = profesorManager.obtenerTodos();
        for (Profesor p : profesores) {
            box_profesor.addItem(p.getId());  // Solo agregas el ID
        }
    }


}


