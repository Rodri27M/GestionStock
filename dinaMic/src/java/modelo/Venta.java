/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author julie
 */

public class Venta {
    private int id_venta;
    private String fecha_venta;
    private int id_usuario;
    private double total;

    public int getId_venta() { return id_venta; }
    public void setId_venta(int id_venta) { this.id_venta = id_venta; }

    public String getFecha_venta() { return fecha_venta; }
    public void setFecha_venta(String fecha_venta) { this.fecha_venta = fecha_venta; }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}

