/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorDAO;

import modelo.Conexion;
import modelo.Actividad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Actividad;
import modelo.CRUD;
import modelo.Conexion;

public class ActividadDAO implements CRUD<Actividad> {
    
    private Connection connection;
    
    public ActividadDAO() {
        connection = null;
    }
    
    @Override
    public List<Actividad> listar() {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT * FROM actividades ORDER BY nom_actividad";
        
        try {
            Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Actividad actividad = new Actividad();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                actividad.setNom_actividad(rs.getString("nom_actividad"));
                actividad.setEnlace(rs.getString("enlace"));
                
                actividades.add(actividad);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar actividades: " + e.getMessage());
        } finally {
        
        }
        
        return actividades;
    }
    
    @Override
    public Actividad listarPorId(int id) {
        Actividad actividad = null;
        String sql = "SELECT * FROM actividades WHERE id_actividad = ?";
        
        try {
            Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                actividad = new Actividad();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                actividad.setNom_actividad(rs.getString("nom_actividad"));
                actividad.setEnlace(rs.getString("enlace"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener actividad por ID: " + e.getMessage());
        } finally {
          
        }
        
        return actividad;
    }
    
    @Override
    public boolean agregar(Actividad actividad) {
        // Las actividades son fijas, no se pueden agregar
        return false;
    }
    
    @Override
    public boolean editar(Actividad actividad) {
        // Las actividades son fijas, no se pueden editar
        return false;
    }
    
    @Override
    public boolean eliminar(int id) {
        // Las actividades son fijas, no se pueden eliminar
        return false;
    }
    
    // MÉTODO ADICIONAL: Obtener actividad por enlace
    public Actividad obtenerPorEnlace(String enlace) {
        Actividad actividad = null;
        String sql = "SELECT * FROM actividades WHERE enlace = ?";
        
        try {
             Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setString(1, enlace);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                actividad = new Actividad();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                actividad.setNom_actividad(rs.getString("nom_actividad"));
                actividad.setEnlace(rs.getString("enlace"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener actividad por enlace: " + e.getMessage());
        } finally {
         
        }
        
        return actividad;
    }
}