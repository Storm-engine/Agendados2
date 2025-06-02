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
    private String restriccionHoraria;

    public Profesor(int id, String nombre, String correo, String restriccionHoraria) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.restriccionHoraria = restriccionHoraria;
    }

    public String getRestriccionHoraria() {
        return restriccionHoraria;
    }

    public void setRestriccionHoraria(String restriccionHoraria) {
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
