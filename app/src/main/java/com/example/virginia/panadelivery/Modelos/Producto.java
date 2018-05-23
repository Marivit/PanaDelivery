package com.example.virginia.panadelivery.Modelos;

public class Producto {

     String nombre, proveedor;
     int cantidad;

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", proveedor='" + proveedor + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }

    Producto() {

    }

    public Producto(String nombre, String proveedor, int cantidad) {
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
