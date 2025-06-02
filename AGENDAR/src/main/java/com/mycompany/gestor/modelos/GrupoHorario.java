/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.modelos;

import java.util.List;

/**
 *
 * @author Sergio
 */
public class GrupoHorario {
    private final int idGrupo;
    private final int idMateria;
    private final int idProfesor;
    private final int cargaHoraria;       // 4 o 6
    private final int bloquesNecesarios;   // cargaHoraria / 2
    private final List<Integer> estudiantes; // IDs de los estudiantes en este grupo

    public GrupoHorario(int idGrupo,
                        int idMateria,
                        int idProfesor,
                        int cargaHoraria,
                        List<Integer> estudiantes) {
        this.idGrupo = idGrupo;
        this.idMateria = idMateria;
        this.idProfesor = idProfesor;
        this.cargaHoraria = cargaHoraria;
        this.bloquesNecesarios = cargaHoraria / 2;
        this.estudiantes = estudiantes;
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

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public int getBloquesNecesarios() {
        return bloquesNecesarios;
    }

    public List<Integer> getEstudiantes() {
        return estudiantes;
    }
}
