package com.example.virginia.panadelivery.Modelos;

public class Pedido {

     String direccion, hora, fecha, ubicacionDireccion, latitud, longitud;
     int numPedido;

    @Override
    public String toString() {
        return "Pedido{" +
                "numPedido='" + numPedido + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha=" + fecha + '\'' +
                ", hora=" + hora + '\'' +
                ", latitud=" + latitud + '\'' +
                ", longitud=" + longitud +
                '}';
    }

    Pedido() {

    }

    public Pedido(String direccion, String fecha, String hora, int numPedido, String latitud, String longitud) {
        this.direccion = direccion;
        this.fecha = fecha;
        this.hora = hora;
        this.numPedido = numPedido;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() { return hora; }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getNumPedido() { return numPedido; }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public String getUbicacion() { return hora; }

    public String getLatitud() { return latitud; }

    public String getLongitud() { return longitud; }


    /*public void setUbicacion(String ubicacionDireccion) { this.ubicacionDireccion = ubicacionDireccion; }*/

}
