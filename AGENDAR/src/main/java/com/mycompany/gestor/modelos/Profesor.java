/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.modelos;

/**
 *
 * @author USER
 */
public class Profesor {
    private int id;
    private String nombre;
    private String correo;
    private restriccion_horaria restriccionHoraria;
    
    public enum restriccion_horaria {
        MAÑANA, TARDE, NOCHE, NINGUNA
    }

    public Profesor(int id, String nombre, String correo, restriccion_horaria restriccionHoraria) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.restriccionHoraria = restriccionHoraria;
    }

    public restriccion_horaria getRestriccionHoraria() {
        return restriccionHoraria;
    }

    public void setRestriccionHoraria(restriccion_horaria restriccionHoraria) {
        this.restriccionHoraria = restriccionHoraria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

 
}
