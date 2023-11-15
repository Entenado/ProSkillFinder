package com.example.demo.entidades;

import com.example.demo.Enumeraciones.Rol;

import jakarta.persistence.Entity;

@Entity
public class Usuario extends Persona {

    private String direccion;

    public Usuario() {
    }

    public Usuario(String direccion, Long id, String nombre, String email, Rol rol, String password, Long telefono) {
        super();
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
