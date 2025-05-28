/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.modelos;

/**
 *
 * @author Sergio David
 */
public class CarreraMateria {
    private int idCarrera;
    private int idMateria;
    private int semestre;

    public CarreraMateria(int idCarrera, int idMateria, int semestre) {
        this.idCarrera = idCarrera;
        this.idMateria = idMateria;
        this.semestre = semestre;
    }

    public int getIdCarrera() { return idCarrera; }
    public void setIdCarrera(int idCarrera) { this.idCarrera = idCarrera; }

    public int getIdMateria() { return idMateria; }
    public void setIdMateria(int idMateria) { this.idMateria = idMateria; }

    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }

    @Override
    public String toString() {
        return "CarreraMateria{" +
                "idCarrera=" + idCarrera +
                ", idMateria=" + idMateria +
                ", semestre=" + semestre +
                '}';
    }
}

