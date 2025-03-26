/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.modelos;

/**
 *
 * @author Sergio David
 */
public class Materia {
    private int id;
    private String nombre;
    private int carga_horaria;

    public Materia(int id, String nombre, int carga_horaria) {
        this.id = id;
        this.nombre = nombre;
        this.carga_horaria = carga_horaria;
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

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(int carca_horaria) {
        this.carga_horaria = carca_horaria;
    }

    @Override
    public String toString() {
        return "Materia{" + "id=" + id + ", nombre=" + nombre + ", carga_horaria=" + carga_horaria + '}';
    }
    
}
