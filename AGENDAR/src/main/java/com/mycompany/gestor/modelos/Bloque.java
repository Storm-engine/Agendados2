/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.modelos;

/**
 *
 * @author Sergio
 */
public class Bloque {
    private int id;         // 0 a 35
    private int dia;        // 0 = lunes, ... 5 = sábado
    private int horaInicio; // Hora en formato 24h, ejemplo 6, 8, 10 ...
    private int horaFin;    // horaInicio + 2

    public Bloque(int id, int dia, int horaInicio) {
        this.id = id;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaInicio + 2;
    }

    public int getId() { return id; }
    public int getDia() { return dia; }
    public int getHoraInicio() { return horaInicio; }
    public int getHoraFin() { return horaFin; }

    @Override
    public String toString() {
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        return dias[dia] + " " + horaInicio + ":00-" + horaFin + ":00";
    }
}

