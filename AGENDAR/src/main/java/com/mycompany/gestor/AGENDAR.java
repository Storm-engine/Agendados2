package com.mycompany.gestor;

import com.mycompany.gestor.controladores.ViewController;
import com.mycompany.gestor.vistas.LogIn;


public class AGENDAR {
    public static void main(String[] args) {
        ViewController views = new ViewController();
        views.agregarVista("login", new LogIn());
        views.cambiarVista("login");
    }
}
