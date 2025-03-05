/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor.controladoresconsultas;

import com.mycompany.gestor.controladores.ConexionDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Consultas {
    public static void mostrarProfesores() {
        Connection conexion = ConexionDB.obtenerConexion();
        if (conexion == null) {
            System.out.println("❌ No se pudo obtener la conexión.");
            return;
        }

        String sql = "SELECT * FROM profesores";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("📜 Lista de Profesores:");

            while (rs.next()) {
                int id = rs.getInt("id_profesor");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String restriccion = rs.getString("restriccion_horaria");

                System.out.println("🆔 ID: " + id + " | 👤 Nombre: " + nombre +
                        " | 📧 Correo: " + correo + " | ⏳ Restricción: " + restriccion);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al consultar profesores: " + e.getMessage());
        }
    }
}
