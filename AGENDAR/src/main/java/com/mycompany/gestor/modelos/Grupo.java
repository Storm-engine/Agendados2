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
    private int idMateria;
    private int idProfesor;

    public Grupo(int idGrupo, int idMateria, int idProfesor) {
        this.idGrupo = idGrupo;
        this.idMateria = idMateria;
        this.idProfesor = idProfesor;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }
}