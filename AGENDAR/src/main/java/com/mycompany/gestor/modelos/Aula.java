/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.modelos;

/**
 *
 * @author Sergio David
 */
public class Aula {
    private int idAula;
    private String tipoAula;  // ahora es String
    private int capacidad;
    private String nombre;

    public Aula(int idAula, String tipoAula, int capacidad, String nombre) {
        this.idAula = idAula;
        this.tipoAula = tipoAula;
        this.capacidad = capacidad;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdAula() {
        return idAula;
    }

    public void setIdAula(int idAula) {
        this.idAula = idAula;
    }

    public String getTipoAula() {
        return tipoAula;
    }

    public void setTipoAula(String tipoAula) {
        this.tipoAula = tipoAula;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}