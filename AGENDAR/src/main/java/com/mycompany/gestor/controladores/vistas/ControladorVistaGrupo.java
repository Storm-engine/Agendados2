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
import com.mycompany.gestor.modelos.Grupo;
import com.mycompany.gestor.modelos.Materia;
import com.mycompany.gestor.modelos.Profesor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ControladorVistaGrupo {
    GrupoManager grupoManager = new GrupoManager();

    public List<Grupo> consultar(JTable tabla) {
        List<Grupo> grupos = grupoManager.obtenerTodos();

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        for (Grupo grupo : grupos) {
            modelo.addRow(new Object[]{
                grupo.getIdGrupo(),
                grupo.getMateria().getId(),
                grupo.getMateria().getNombre(),
                grupo.getMateria().getCarga_horaria(),
                grupo.getProfesor().getId(),
                grupo.getProfesor().getNombre(),
                grupo.getProfesor().getCorreo(),
                grupo.getProfesor().getRestriccionHoraria()
            });
        }

        return grupos;
    }

    public void insertar(int idGrupo, Materia materia, Profesor profesor) {
        Grupo grupo = new Grupo(idGrupo, materia, profesor);
        grupoManager.insertar(grupo);
    }

    public void eliminar(int idGrupo) {
        grupoManager.eliminar(idGrupo);
    }
}
