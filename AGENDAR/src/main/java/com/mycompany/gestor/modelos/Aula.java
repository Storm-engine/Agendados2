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
    public enum TipoAula {
    COMUN, INFORMATICA, LABORATORIO;
    }
    private TipoAula tipoAula;
    private int capacidad;

    public Aula(int idAula, TipoAula tipoAula, int capacidad) {
        this.idAula = idAula;
        this.tipoAula = tipoAula;
        this.capacidad = capacidad;
    }

    // Getters y Setters
    public int getIdAula() {
        return idAula;
    }

    public void setIdAula(int idAula) {
        this.idAula = idAula;
    }

    public TipoAula getTipoAula() {
        return tipoAula;
    }

    public void setTipoAula(TipoAula tipoAula) {
        this.tipoAula = tipoAula;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
