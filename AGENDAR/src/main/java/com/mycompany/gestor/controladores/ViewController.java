package com.mycompany.gestor.controladores;

import javax.swing.*;
import java.awt.*;

public class ViewController {
    private static JFrame mainFrame;
    private static JPanel mainPanel;

    public ViewController() {
        mainFrame = new JFrame("Gestor de Tareas");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 400);
        mainFrame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new CardLayout()); // Usa CardLayout para cambiar vistas
        mainFrame.add(mainPanel);
    }

    /**
     * Agrega una vista al controlador.
     */
    public void agregarVista(String nombre, JPanel panel) {
        mainPanel.add(panel, nombre);
    }

    /**
     * Cambia la vista actual.
     */
    public void cambiarVista(String nombre) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, nombre);
    }

    /**
     * Muestra la ventana principal.
     */
    public void mostrar() {
        mainFrame.setVisible(true);
    }
}
