package com.mycompany.gestor;

import com.mycompany.gestor.controladores.ViewController;

public class AGENDAR {
    public static void main(String[] args) {
        ViewController vc = ViewController.get_instance();
        vc.inicializar();
        vc.cambiarVista("menu");
    }
}
