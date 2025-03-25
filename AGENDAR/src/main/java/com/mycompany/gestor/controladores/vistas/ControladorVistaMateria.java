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
public class ControladorVistaMateria {
    MateriaManager em = new MateriaManager();
    public void contultar(){
        System.out.println(em.obtenerTodos());
    }
    public void insertar(int id, String nombre, int carga_horaria){
        Materia materia = new Materia(id,  nombre, carga_horaria);
        em.insertar(materia);
    }
}
