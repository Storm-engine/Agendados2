/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores.vistas;

import com.mycompany.gestor.controladoresconsultas.ProfesorManager;
import com.mycompany.gestor.modelos.Profesor;

/**
 *
 * @author USER
 */
public class ControladorVistaProfesor {
    ProfesorManager pm = new ProfesorManager();
    public void consultar(){
        System.out.println(pm.obtenerTodos());
    }
    public void insertar(int id, String nombre, String correo, String restriccion){
        Profesor.restriccion_horaria restriccionEnum = Profesor.restriccion_horaria.valueOf(restriccion.toUpperCase());
        Profesor profesor = new Profesor(id, nombre, correo, restriccionEnum);
        pm.insertar(profesor);
    }
}
