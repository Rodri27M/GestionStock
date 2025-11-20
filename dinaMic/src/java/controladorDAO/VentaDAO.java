/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorDAO;

import modelo.Conexion;
import modelo.CRUD;
import modelo.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO implements CRUD<Venta> {

    @Override
    public List<Venta> listar() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas ORDER BY fecha_venta DESC";

        try (Connection con = new Conexion().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Venta v = new Venta();
                v.setId_venta(rs.getInt("id_venta"));
                v.setFecha_venta(rs.getString("fecha_venta"));
                v.setId_usuario(rs.getInt("id_usuario"));
                v.setTotal(rs.getDouble("total"));
                ventas.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error listar ventas: " + e.getMessage());
        }
        return ventas;
    }

    @Override
    public Venta listarPorId(int id) {
        Venta v = null;
        String sql = "SELECT * FROM ventas WHERE id_venta = ?";

        try (Connection con = new Conexion().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    v = new Venta();
                    v.setId_venta(rs.getInt("id_venta"));
                    v.setFecha_venta(rs.getString("fecha_venta"));
                    v.setId_usuario(rs.getInt("id_usuario"));
                    v.setTotal(rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error listar venta por ID: " + e.getMessage());
        }
        return v;
    }

    @Override
    public boolean agregar(Venta v) {
        String sql = "INSERT INTO ventas (fecha_venta, id_usuario, total) VALUES (?, ?, ?)";

        try (Connection con = new Conexion().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getFecha_venta());
            ps.setInt(2, v.getId_usuario());
            ps.setDouble(3, v.getTotal());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error agregar venta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean editar(Venta v) {
        String sql = "UPDATE ventas SET fecha_venta=?, id_usuario=?, total=? WHERE id_venta=?";

        try (Connection con = new Conexion().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getFecha_venta());
            ps.setInt(2, v.getId_usuario());
            ps.setDouble(3, v.getTotal());
            ps.setInt(4, v.getId_venta());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error editar venta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM ventas WHERE id_venta=?";

        try (Connection con = new Conexion().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error eliminar venta: " + e.getMessage());
            return false;
        }
    }
}
