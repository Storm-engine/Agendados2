package com.mycompany.gestor.controladores;

import javax.swing.*;
import java.util.HashMap;

public class ViewController {
    private static HashMap<String, JFrame> vistas; // Mapa para manejar ventanas
    private static JFrame ventanaActual; // La ventana que está visible

    public ViewController() {
        vistas = new HashMap<>();
    }

    /**
     * Registra una vista en el sistema.
     * @param nombre Nombre de la vista
     * @param ventana La instancia de la ventana (JFrame)
     */
    public void agregarVista(String nombre, JFrame ventana) {
        vistas.put(nombre, ventana);
    }

    /**
     * Cambia la vista activa, cerrando la actual y abriendo la nueva.
     * @param nombre Nombre de la vista a mostrar
     */
    public void cambiarVista(String nombre) {
        if (!vistas.containsKey(nombre)) {
            System.out.println("⚠️ Error: La vista '" + nombre + "' no está registrada.");
            return;
        }

        // Cerrar la ventana actual si existe
        if (ventanaActual != null) {
            ventanaActual.setVisible(false);
        }

        // Abrir la nueva ventana
        ventanaActual = vistas.get(nombre);
        ventanaActual.setVisible(true);
    }
}
