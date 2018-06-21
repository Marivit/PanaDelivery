package com.example.virginia.panadelivery.Modelos;

import android.util.Log;

import java.util.List;

public class Panaderia {

    String nombre, direccion, id, latitud, longitud;
    List<Producto> productosPanaderia;

    public void setProductosPanaderia(List<Producto> productosPanaderia) {
        this.productosPanaderia = productosPanaderia;
        Log.d("HOLA", "SE AGREGARON LOS PRODUCTOS");
        if (productosPanaderia.size() != 0) {
            Log.d("p0", productosPanaderia.get(0).getNombre());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Producto> getProductosPanaderia() {
        return productosPanaderia;
    }

    Panaderia() {

    }

    public Panaderia(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Panaderia{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
