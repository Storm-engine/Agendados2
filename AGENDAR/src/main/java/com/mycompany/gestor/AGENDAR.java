package com.mycompany.gestor;

import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.controladores.ViewController;
import java.sql.SQLException;


public class AGENDAR {
    public static void main(String[] args) throws SQLException {
        Conexion.getConnection();
        ViewController vc = ViewController.get_instance();
        vc.inicializar();
        vc.cambiarVista("menu");
    }
    
    
}
