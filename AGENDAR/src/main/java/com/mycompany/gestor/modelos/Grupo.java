/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.modelos;

/**
 *
 * @author Sergio
 */
public class Grupo {
    private int idGrupo;
    private Materia materia;
    private Profesor profesor;

    public Grupo(int idGrupo, Materia materia, Profesor profesor) {
        this.idGrupo = idGrupo;
        this.materia = materia;
        this.profesor = profesor;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public Materia getMateria() {
        return materia;
    }

    public Profesor getProfesor() {
        return profesor;
    }
}
