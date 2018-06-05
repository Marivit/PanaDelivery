package com.example.virginia.panadelivery.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {

     String nombre, proveedor, id ;
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
                ", proveedor='" + proveedor + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }

    public Producto() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public Producto(Parcel in) {
        this.nombre = in.readString();
        this.proveedor = in.readString();
        this.id = in.readString();
        this.cantidad = in.readInt();

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(proveedor);
        dest.writeString(id);
        dest.writeInt(cantidad);

    }
}
