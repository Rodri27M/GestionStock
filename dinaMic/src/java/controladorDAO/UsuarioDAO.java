
package controladorDAO;

import modelo.Conexion;
import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Actividad;
import modelo.CRUD; 

public class UsuarioDAO implements CRUD<Usuario> {
  
    public UsuarioDAO() {
    
    }
    
//    // Método de autenticación
//    public Usuario autenticar(String usuario, String clave) {
//        Usuario user = null;
//        String sql = "SELECT u.*, p.perfil FROM usuarios u " +
//                    "INNER JOIN perfiles p ON u.id_perfil = p.id_perfil " +
//                    "WHERE u.usuario = ? AND u.clave = ?";
//        
//        try {
//            Conexion connection = new Conexion();
//            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
//            ps.setString(1, usuario);
//            ps.setString(2, clave);
//            
//            ResultSet rs = ps.executeQuery();
//            
//            if (rs.next()) {
//                user = new Usuario();
//                user.setIdusu(rs.getInt("idusu"));
//                user.setNum_docu(rs.getString("num_docu"));
//                user.setNombre(rs.getString("nombre"));
//                user.setApellido(rs.getString("apellido"));
//                user.setEmail(rs.getString("email"));
//                user.setUsuario(rs.getString("usuario"));
//                user.setId_perfil(rs.getInt("id_perfil"));
//                user.setNombrePerfil(rs.getString("perfil"));
//            }
//            
//        } catch (SQLException e) {
//            System.err.println("Error en autenticación: " + e.getMessage());
//        } finally {
//    
//        }
//        
//        return user;
//    }
    
    // Implementación de métodos CRUD
    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, p.perfil FROM usuarios u " +
                    "INNER JOIN perfiles p ON u.id_perfil = p.id_perfil";
        
        try {
           Conexion  connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Usuario user = new Usuario();
                user.setIdusu(rs.getInt("idusu"));
                user.setNum_docu(rs.getString("num_docu"));
                user.setNombre(rs.getString("nombre"));
                user.setApellido(rs.getString("apellido"));
                user.setEmail(rs.getString("email"));
                user.setUsuario(rs.getString("usuario"));
                user.setId_perfil(rs.getInt("id_perfil"));
                user.setNombrePerfil(rs.getString("perfil"));
                
                usuarios.add(user);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        } finally {

        }
        
        return usuarios;
    }
    
 @Override
    public boolean agregar(Usuario usuario) {
        boolean exito = false;
        String sql = "INSERT INTO usuarios (num_docu, nombre, apellido, email, usuario, clave, id_perfil) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
          Conexion  connection = new  Conexion();
            
            // Verificar si el usuario o email ya existen
            if (existeUsuario(usuario.getUsuario()) || existeEmail(usuario.getEmail())) {
                return false;
            }
            
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setString(1, usuario.getNum_docu());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getUsuario());
            ps.setString(6, usuario.getClave()); // En texto plano por ahora
            ps.setInt(7, usuario.getId_perfil());
            
            int filasAfectadas = ps.executeUpdate();
            exito = (filasAfectadas > 0);
            
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
        
        }
        
        return exito;
    }
    
    // Método para verificar si el usuario ya existe
    private boolean existeUsuario(String usuario) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";
        
        try {
            Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar usuario: " + e.getMessage());
        }
        
        return false;
    }
    
    // Método para verificar si el email ya existe
    private boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        
        try {
            Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar email: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean editar(Usuario usuario) {
        boolean exito = false;
        String sql = "UPDATE usuarios SET num_docu = ?, nombre = ?, apellido = ?, " +
                    "email = ?, usuario = ?, id_perfil = ? WHERE idusu = ?";
        
        try {
           Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setString(1, usuario.getNum_docu());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getUsuario());
            ps.setInt(6, usuario.getId_perfil());
            ps.setInt(7, usuario.getIdusu());
            
            int filasAfectadas = ps.executeUpdate();
            exito = (filasAfectadas > 0);
            
        } catch (SQLException e) {
            System.err.println("Error al editar usuario: " + e.getMessage());
        } finally {
        
        }
        
        return exito;
    }
    
    @Override
    public boolean eliminar(int id) {
        boolean exito = false;
        String sql = "DELETE FROM usuarios WHERE idusu = ?";
        
        try {
          Conexion  connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            
            int filasAfectadas = ps.executeUpdate();
            exito = (filasAfectadas > 0);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        } finally {
         
        }
        
        return exito;
    }
    
    @Override
    public Usuario listarPorId(int id) {
        Usuario user = null;
        String sql = "SELECT u.*, p.perfil FROM usuarios u " +
                    "INNER JOIN perfiles p ON u.id_perfil = p.id_perfil " +
                    "WHERE u.idusu = ?";
        
        try {
           Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                user = new Usuario();
                user.setIdusu(rs.getInt("idusu"));
                user.setNum_docu(rs.getString("num_docu"));
                user.setNombre(rs.getString("nombre"));
                user.setApellido(rs.getString("apellido"));
                user.setEmail(rs.getString("email"));
                user.setUsuario(rs.getString("usuario"));
                user.setId_perfil(rs.getInt("id_perfil"));
                user.setNombrePerfil(rs.getString("perfil"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario por ID: " + e.getMessage());
        } finally {
           
        }
        
        return user;
    }
      public Usuario autenticar(String usuario, String clave) {
        Usuario user = null;
        String sql = "SELECT u.*, p.perfil FROM usuarios u " +
                    "INNER JOIN perfiles p ON u.id_perfil = p.id_perfil " +
                    "WHERE u.usuario = ? AND u.clave = ?";
        
        try {
            Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clave);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                user = new Usuario();
                user.setIdusu(rs.getInt("idusu"));
                user.setNum_docu(rs.getString("num_docu"));
                user.setNombre(rs.getString("nombre"));
                user.setApellido(rs.getString("apellido"));
                user.setEmail(rs.getString("email"));
                user.setUsuario(rs.getString("usuario"));
                user.setId_perfil(rs.getInt("id_perfil"));
                user.setNombrePerfil(rs.getString("perfil"));
                
                // CARGAR ACTIVIDADES PERMITIDAS PARA ESTE USUARIO
                List<Actividad> actividades = obtenerActividadesPorUsuario(user.getIdusu());
                user.setActividades(actividades);
            }
            
        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
        } finally {
//            Conexion.closeConnection(connection);
        }
        
        return user;
    }
    
    // NUEVO MÉTODO - OBTENER ACTIVIDADES POR USUARIO
    public List<Actividad> obtenerActividadesPorUsuario(int idUsuario) {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT a.* FROM actividades a " +
                    "INNER JOIN gesActividad g ON a.id_actividad = g.id_actividad " +
                    "WHERE g.id_usuario = ?";
        
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
            System.err.println("Error al obtener actividades: " + e.getMessage());
        } finally {
           
        }
        
        return actividades;
    }
    
    // MÉTODO PARA VERIFICAR SI USUARIO TIENE ACCESO A UNA ACTIVIDAD
    public boolean tieneAcceso(int idUsuario, String enlaceActividad) {
        String sql = "SELECT COUNT(*) FROM gesActividad g " +
                    "INNER JOIN actividades a ON g.id_actividad = a.id_actividad " +
                    "WHERE g.id_usuario = ? AND a.enlace = ?";
        
        try {
           Conexion connection = new Conexion();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setString(2, enlaceActividad);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar acceso: " + e.getMessage());
        } finally {
     
        }
        
        return false;
    }
    
    // ... (los demás métodos CRUD se mantienen igual)
}
