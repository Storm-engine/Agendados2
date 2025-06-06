package com.mycompany.gestor.controladores;

import com.mycompany.gestor.vistas.Vista_Aula;
import com.mycompany.gestor.vistas.Vista_Carreras;
import com.mycompany.gestor.vistas.Vista_Estudiante;
import com.mycompany.gestor.vistas.Vista_Grupos;
import com.mycompany.gestor.vistas.Vista_Horarios;
import com.mycompany.gestor.vistas.Vista_Materia;
import com.mycompany.gestor.vistas.Vista_MateriasCarreras;
import com.mycompany.gestor.vistas.Vista_Menu;
import com.mycompany.gestor.vistas.Vista_Profesor;
import javax.swing.*;
import java.util.HashMap;

public class ViewController {
    public static HashMap<String, JFrame> vistas;
    private static JFrame ventanaActual; 
    private static ViewController vc;
    public ViewController() {
        vistas = new HashMap<>();
    }

    
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
        agregarVista("grupos", new Vista_Grupos());
        agregarVista("aula", new Vista_Aula());
        agregarVista("horario", new Vista_Horarios());
    }
    
    public void cambiarVista(String nombre) {
        if (!vistas.containsKey(nombre)) {
            System.out.println("Error: La vista '" + nombre + "' no esta registrada.");
            return;
        }

        
        if (ventanaActual != null) {
            ventanaActual.setVisible(false);
        }

        
        ventanaActual = vistas.get(nombre);
        ventanaActual.setVisible(true);
    }
}
