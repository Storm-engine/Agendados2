/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.modelos;

/**
 *
 * @author EQUIPO1
 */
public class Estudiante {
    private int id;
    private String nombre;
    private String correo;
    private int semestre;
    private int idCarrera;

    public Estudiante(int id, String nombre, String correo, int semestre, int idCarrera) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.semestre = semestre;
        this.idCarrera = idCarrera;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }

    public int getIdCarrera() { return idCarrera; }
    public void setIdCarrera(int idCarrera) { this.idCarrera = idCarrera; }
}

