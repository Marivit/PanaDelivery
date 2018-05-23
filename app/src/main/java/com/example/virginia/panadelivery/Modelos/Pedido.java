package com.example.virginia.panadelivery.Modelos;

public class Pedido {

     String direccion, hora, fecha;
     int numPedido;

    @Override
    public String toString() {
        return "Pedido{" +
                "numPedido='" + numPedido + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }

    Pedido() {

    }

    public Pedido(String direccion, String fecha, String hora, int numPedido) {
        this.direccion = direccion;
        this.fecha = fecha;
        this.hora = hora;
        this.numPedido = numPedido;
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
}
