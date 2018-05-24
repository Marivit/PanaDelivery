package com.example.virginia.panadelivery.Modelos;

public class Usuario {

     String email;

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email +
                '}';
    }

    Usuario() {

    }

    public Usuario(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
