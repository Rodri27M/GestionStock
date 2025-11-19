// modelo/dao/GesActividadDAO.java
package controladorDAO;

import modelo.Conexion;
import modelo.Actividad;
import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Actividad;
import modelo.Conexion;
import modelo.Usuario;

public class GesActividadDAO {
    
 
    
    
    // ASIGNAR ACTIVIDAD A USUARIO
    public boolean asignarActividadUsuario(int idUsuario, int idActividad) {
        boolean exito = false;
        String sql = "INSERT INTO gesactividad (id_usuario, id_actividad) VALUES (?, ?)";
        
        try {
           Conexion connection = new Conexion();
            
            // Verificar si ya existe la asignación
            if (!existeAsignacion(idUsuario, idActividad)) {
                PreparedStatement ps = connection.getConnection().prepareStatement(sql);
                ps.setInt(1, idUsuario);
                ps.setInt(2, idActividad);
                
                int filasAfectadas = ps.executeUpdate();
                exito = (filasAfectadas > 0);
            }
            
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
         
        }
        
        return exito;
    }
    
    // ELIMINAR ACTIVIDAD DE USUARIO
    public boolean eliminarActividadUsuario(int idUsuario, int idActividad) {
        boolean exito = false;
        String sql = "DELETE FROM gesactividad WHERE id_usuario = ? AND id_actividad = ?";
        
        try {
              Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idActividad);
            
            int filasAfectadas = ps.executeUpdate();
            exito = (filasAfectadas > 0);
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar actividad: " + e.getMessage());
        } finally {
       
        }
        
        return exito;
    }
    
    // OBTENER ACTIVIDADES DE UN USUARIO
    public List<Actividad> obtenerActividadesPorUsuario(int idUsuario) {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT a.* FROM actividades a " +
                    "INNER JOIN gesActividad g ON a.id_actividad = g.id_actividad " +
                    "WHERE g.id_usuario = ? ORDER BY a.nom_actividad";
        
        try {
            Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, idUsuario);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Actividad actividad = new Actividad();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                actividad.setNom_actividad(rs.getString("nom_actividad"));
                actividad.setEnlace(rs.getString("enlace"));
                
                actividades.add(actividad);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener actividades por usuario: " + e.getMessage());
        } finally {
     
        }
        
        return actividades;
    }
    
    // OBTENER USUARIOS POR ACTIVIDAD
    public List<Usuario> obtenerUsuariosPorActividad(int idActividad) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, p.perfil FROM usuarios u " +
                    "INNER JOIN gesActividad g ON u.idusu = g.id_usuario " +
                    "INNER JOIN perfiles p ON u.id_perfil = p.id_perfil " +
                    "WHERE g.id_actividad = ? ORDER BY u.nombre, u.apellido";
        
        try {
              Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, idActividad);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdusu(rs.getInt("idusu"));
                usuario.setNum_docu(rs.getString("num_docu"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setId_perfil(rs.getInt("id_perfil"));
                usuario.setNombrePerfil(rs.getString("perfil"));
                
                usuarios.add(usuario);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios por actividad: " + e.getMessage());
        } finally {
           
        }
        
        return usuarios;
    }
    
    // OBTENER ACTIVIDADES NO ASIGNADAS A UN USUARIO
    public List<Actividad> obtenerActividadesNoAsignadas(int idUsuario) {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT a.* FROM actividades a " +
                    "WHERE a.id_actividad NOT IN (" +
                    "    SELECT g.id_actividad FROM gesActividad g " +
                    "    WHERE g.id_usuario = ?" +
                    ") ORDER BY a.nom_actividad";
        
        try {
            Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, idUsuario);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Actividad actividad = new Actividad();
                actividad.setId_actividad(rs.getInt("id_actividad"));
                actividad.setNom_actividad(rs.getString("nom_actividad"));
                actividad.setEnlace(rs.getString("enlace"));
                
                actividades.add(actividad);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener actividades no asignadas: " + e.getMessage());
        } finally {
      
        }
        
        return actividades;
    }
    
    // ELIMINAR TODAS LAS ACTIVIDADES DE UN USUARIO
    public boolean eliminarTodasActividadesUsuario(int idUsuario) {
        boolean exito = false;
        String sql = "DELETE FROM gesactividad WHERE id_usuario = ?";
        
        try {
            Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, idUsuario);
            
            int filasAfectadas = ps.executeUpdate();
            exito = (filasAfectadas >= 0); // Puede ser 0 si no tenía actividades
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar todas las actividades: " + e.getMessage());
        } finally {
         
        }
        
        return exito;
    }
    
    // VERIFICAR SI EXISTE ASIGNACIÓN
    private boolean existeAsignacion(int idUsuario, int idActividad) {
        String sql = "SELECT COUNT(*) FROM gesactividad WHERE id_usuario = ? AND id_actividad = ?";
        
        try {
            Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idActividad);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar asignación: " + e.getMessage());
        }
        
        return false;
    }
    
    // OBTENER TODAS LAS ASIGNACIONES (para reportes)
    public List<String> obtenerTodasAsignaciones() {
        List<String> asignaciones = new ArrayList<>();
        String sql = "SELECT u.nombre || ' ' || u.apellido as usuario, " +
                    "a.nom_actividad as actividad " +
                    "FROM gesActividad g " +
                    "INNER JOIN usuarios u ON g.id_usuario = u.idusu " +
                    "INNER JOIN actividades a ON g.id_actividad = a.id_actividad " +
                    "ORDER BY u.nombre, a.nom_actividad";
        
        try {
             Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String asignacion = rs.getString("usuario") + " - " + rs.getString("actividad");
                asignaciones.add(asignacion);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las asignaciones: " + e.getMessage());
        } finally {
           
        }
        
        return asignaciones;
    }
}