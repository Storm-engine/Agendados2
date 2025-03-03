/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/horario";
    private static final String USUARIO = "root"; 
    private static final String CONTRASEÑA = "admin";  

    public static Connection obtenerConexion() {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar: " + e.getMessage());
            return null;
        }
    }
}