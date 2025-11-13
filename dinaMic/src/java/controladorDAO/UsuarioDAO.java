
package controladorDAO;

import modelo.Conexion;
import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.CRUD; 

public class UsuarioDAO implements CRUD<Usuario> {
    
    private Connection connection;
    
    public UsuarioDAO() {
        connection = null;
    }
    
    // Método de autenticación
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
            }
            
        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
        } finally {
    
        }
        
        return user;
    }
    
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
            System.err.println("Error al agregar usuario: " + e.getMessage());
        } finally {
        
        }
        
        return exito;
    }
    
    // Método para verificar si el usuario ya existe
    private boolean existeUsuario(String usuario) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
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
            PreparedStatement ps = connection.prepareStatement(sql);
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
}
