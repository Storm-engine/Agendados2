package com.mycompany.gestor.controladores;

import com.mycompany.gestor.vistas.Vista_Carreras;
import com.mycompany.gestor.vistas.Vista_Estudiante;
import com.mycompany.gestor.vistas.Vista_Materia;
import com.mycompany.gestor.vistas.Vista_MateriasCarreras;
import com.mycompany.gestor.vistas.Vista_Menu;
import com.mycompany.gestor.vistas.Vista_Profesor;
import java.util.ArrayList;
import javax.swing.*;
import java.util.HashMap;

public class ViewController {
    public static HashMap<String, JFrame> vistas; // Mapa para manejar ventanas
    private static JFrame ventanaActual; // La ventana que está visible
    private static ViewController vc;
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
    
    public static ViewController get_instance(){
        if (vc == null) {
            vc = new ViewController();
        }
        return vc;
    }
    
    public void inicializar(){
        agregarVista("estudiante", new Vista_Estudiante());
        agregarVista("materia", new Vista_Materia());
        agregarVista("profesor", new Vista_Profesor());
        agregarVista("menu", new Vista_Menu());
        agregarVista("carrera", new Vista_Carreras());
        agregarVista("carrera_materias", new Vista_MateriasCarreras());
    }
    
    public void cambiarVista(String nombre) {
        if (!vistas.containsKey(nombre)) {
            System.out.println("Error: La vista '" + nombre + "' no esta registrada.");
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
