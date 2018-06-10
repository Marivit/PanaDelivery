package com.example.virginia.panadelivery.Modelos;

public class Pedido {

     String direccion, hora, fecha, latitud, longitud, correoCliente;
     String idPedido, conductorEmail;
     int estado;

    @Override
    public String toString() {
        return "Pedido{" +
                "estado='" + estado + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha=" + fecha + '\'' +
                ", hora=" + hora + '\'' +
                ", latitud=" + latitud + '\'' +
                ", longitud=" + longitud + '\'' +
                ", conductorEmail=" + conductorEmail +
                '}';
    }

    public Pedido(String idPedido, int estado) {
        this.idPedido=idPedido;
        this.estado=estado;
    }

    public Pedido() {

    }

    public Pedido(int estado, String direccion, String fecha, String hora, String latitud, String longitud, String conductorEmail) {
        this.estado = estado;
        this.direccion = direccion;
        this.fecha = fecha;
        this.hora = hora;
        this.latitud = latitud;
        this.longitud = longitud;
        this.conductorEmail= conductorEmail;
    }
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
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

    public String getLatitud() { return latitud; }

    public String getConductorEmail() {
        return conductorEmail;
    }

    public void setConductorEmail(String conductorEmail) { this.conductorEmail = conductorEmail; }

    public String getLongitud() { return longitud; }

}
