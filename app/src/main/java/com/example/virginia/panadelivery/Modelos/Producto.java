package com.example.virginia.panadelivery.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {

     String nombre, foto, id, descripcion;
     int cantidad;


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        public Producto[] newArray(int size) {
            return new Producto[size];
        }

    };


    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", proveedor='" + foto + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }

    public Producto() {

    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Producto(String nombre, String proveedor, int cantidad) {
        this.nombre = nombre;
        this.foto = proveedor;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public String getFoto() {
        return foto;
    }

    public void setFoto(String proveedor) {
        this.foto = proveedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    public Producto(Parcel in) {
        this.nombre = in.readString();
        this.foto = in.readString();
        this.id = in.readString();
        this.cantidad = in.readInt();
        this.descripcion = in.readString();

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(foto);
        dest.writeString(id);
        dest.writeInt(cantidad);
        dest.writeString(descripcion);

    }
}
