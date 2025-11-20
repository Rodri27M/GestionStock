package controladorDAO;

import modelo.Conexion;
import modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.CRUD;

public class ProductoDAO implements CRUD<Producto> {
    
    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre as nombre_categoria FROM productos p " +
                    "LEFT JOIN categorias c ON p.id_categoria = c.id_categoria " +
                    "ORDER BY p.nombre";
        
        // CORRECCIÓN: Usar Conexion como instancia
        Conexion conexion = new Conexion();
        Connection connection = null;
        
        try {
            connection = conexion.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setMin_stock(rs.getInt("min_stock"));
                producto.setId_categoria(rs.getInt("id_categoria"));
                producto.setNombreCategoria(rs.getString("nombre_categoria"));
                
                productos.add(producto);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        } finally {
            // CORRECCIÓN: Cerrar la conexión correctamente
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return productos;
    }
    
    @Override
    public Producto listarPorId(int id) {
        Producto producto = null;
        String sql = "SELECT p.*, c.nombre as nombre_categoria FROM productos p " +
                    "LEFT JOIN categorias c ON p.id_categoria = c.id_categoria " +
                    "WHERE p.id_producto = ?";
        
        Conexion conexion = new Conexion();
        Connection connection = null;
        
        try {
            connection = conexion.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setMin_stock(rs.getInt("min_stock"));
                producto.setId_categoria(rs.getInt("id_categoria"));
                producto.setNombreCategoria(rs.getString("nombre_categoria"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener producto por ID: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return producto;
    }
    
    @Override
    public boolean agregar(Producto producto) {
        Conexion conexion = new Conexion();
        Connection connection = null;
        boolean exito = false;
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock, min_stock, id_categoria) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            connection = conexion.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());
            ps.setInt(5, producto.getMin_stock());
            ps.setInt(6, producto.getId_categoria());
            
            int filasAfectadas = ps.executeUpdate();
            exito = (filasAfectadas > 0);
            
        } catch (SQLException e) {
            System.err.println("Error al agregar producto: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return exito;
    }
    
    @Override
    public boolean editar(Producto producto) {
        Conexion conexion = new Conexion();
        Connection connection = null;
        boolean exito = false;
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, " +
                    "stock = ?, min_stock = ?, id_categoria = ? WHERE id_producto = ?";
        
        try {
            connection = conexion.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());
            ps.setInt(5, producto.getMin_stock());
            ps.setInt(6, producto.getId_categoria());
            ps.setInt(7, producto.getId_producto());
            
            int filasAfectadas = ps.executeUpdate();
            exito = (filasAfectadas > 0);
            
        } catch (SQLException e) {
            System.err.println("Error al editar producto: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return exito;
    }
    
    @Override
    public boolean eliminar(int id) {
        Conexion conexion = new Conexion();
        Connection connection = null;
        boolean exito = false;
        String sql = "DELETE FROM productos WHERE id_producto = ?";
        
        try {
            connection = conexion.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            
            int filasAfectadas = ps.executeUpdate();
            exito = (filasAfectadas > 0);
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return exito;
    }
    
    // MÉTODOS ADICIONALES (implementa según necesites)
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        // Implementar según necesidad
        return false;
    }
    
    public List<Producto> buscarPorNombre(String nombre) {
        // Implementar según necesidad
        return new ArrayList<>();
    }
    
    public List<Producto> productosStockBajo() {
        // Implementar según necesidad
        return new ArrayList<>();
    }
    
    public List<Producto> listarPorCategoria(int idCategoria) {
        // Implementar según necesidad
        return new ArrayList<>();
    }
}