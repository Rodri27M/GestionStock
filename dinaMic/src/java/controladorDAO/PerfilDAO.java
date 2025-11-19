/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorDAO;

import modelo.Conexion;
import modelo.Perfil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.CRUD;
import modelo.Conexion;
import modelo.Perfil;

public class PerfilDAO implements CRUD<Perfil> {
    
    private Connection connection;
    
    public PerfilDAO() {
        connection = null;
    }
    
    @Override
    public List<Perfil> listar() {
        List<Perfil> perfiles = new ArrayList<>();
        String sql = "SELECT * FROM perfiles ORDER BY id_perfil";
        
        try {
           Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Perfil perfil = new Perfil();
                perfil.setId_perfil(rs.getInt("id_perfil"));
                perfil.setPerfil(rs.getString("perfil"));
                
                perfiles.add(perfil);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar perfiles: " + e.getMessage());
        } finally {
         
        }
        
        return perfiles;
    }
    
    @Override
    public Perfil listarPorId(int id) {
        Perfil perfil = null;
        String sql = "SELECT * FROM perfiles WHERE id_perfil = ?";
        
        try {
            Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                perfil = new Perfil();
                perfil.setId_perfil(rs.getInt("id_perfil"));
                perfil.setPerfil(rs.getString("perfil"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener perfil por ID: " + e.getMessage());
        } finally {
          
        }
        
        return perfil;
    }
    
    @Override
    public boolean agregar(Perfil perfil) {
        // Los perfiles son fijos, no se pueden agregar
        return false;
    }
    
    @Override
    public boolean editar(Perfil perfil) {
        // Los perfiles son fijos, no se pueden editar
        return false;
    }
    
    @Override
    public boolean eliminar(int id) {
        // Los perfiles son fijos, no se pueden eliminar
        return false;
    }
}