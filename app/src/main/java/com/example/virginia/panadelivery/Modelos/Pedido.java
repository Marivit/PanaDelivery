package com.example.virginia.panadelivery.Modelos;

public class Pedido {

     String direccion, hora, fecha, ubicacionDireccion, latitud, longitud;
     String idPedido;

    @Override
    public String toString() {
        return "Pedido{" +
                ", direccion='" + direccion + '\'' +
                ", fecha=" + fecha + '\'' +
                ", hora=" + hora + '\'' +
                ", latitud=" + latitud + '\'' +
                ", longitud=" + longitud +
                '}';
    }

    Pedido() {

    }

    public Pedido(String direccion, String fecha, String hora, String latitud, String longitud) {
        this.direccion = direccion;
        this.fecha = fecha;
        this.hora = hora;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
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

    public String getUbicacion() { return hora; }

    public String getLatitud() { return latitud; }

    public String getLongitud() { return longitud; }


    /*public void setUbicacion(String ubicacionDireccion) { this.ubicacionDireccion = ubicacionDireccion; }*/

}
