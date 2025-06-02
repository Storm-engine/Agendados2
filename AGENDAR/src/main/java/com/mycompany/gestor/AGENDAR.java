package com.mycompany.gestor;

import Horarios.GeneradorHorario;
import com.mycompany.gestor.controladores.Conexion;
import com.mycompany.gestor.controladores.ViewController;
import com.mycompany.gestor.modelos.Bloque;
import java.sql.SQLException;
import java.util.List;

public class AGENDAR {
    public static void main(String[] args) throws SQLException {
        Conexion.getConnection();
        ViewController vc = ViewController.get_instance();
        vc.inicializar();
        vc.cambiarVista("menu");
    }
    
    
}
