/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;

import com.mycompany.gestor.controladoresconsultas.EstudianteManager;
import com.mycompany.gestor.modelos.Estudiante;

/**
 *
 * @author Sergio David
 */
public class ControladorVistaEstudiante {
    EstudianteManager em = new EstudianteManager();
    public void contultar(){
        System.out.println(em.obtenerTodos());
    }
    public void insertar(int id, String nombre, String correo, int semestre){
        Estudiante estudiante = new Estudiante( id,  nombre,  correo,  semestre);
        em.insertar(estudiante);
    }
}
