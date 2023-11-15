package com.example.demo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Proveedor extends Persona {

    private String direccion;
    private float honorarioHora;
    private String rubro;
    private String presentacion;
    private double promedioPuntaje;
    @OneToOne
    private Imagen imagen;

    public Proveedor() {

    }

    public Proveedor(String direccion, float honorarioHora, String rubro,
            String presentacion, double promedioPuntaje) {
        this.direccion = direccion;
        this.honorarioHora = honorarioHora;
        this.rubro = rubro;
        this.presentacion = presentacion;
        this.promedioPuntaje = promedioPuntaje;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getHonorarioHora() {
        return honorarioHora;
    }

    public void setHonorarioHora(float honorarioHora) {
        this.honorarioHora = honorarioHora;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public double getPromedioPuntaje() {
        return promedioPuntaje;
    }

    public void setPromedioPuntaje(double promedioPuntaje) {
        this.promedioPuntaje = promedioPuntaje;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Proveedor [direccion=" + direccion + ", honorarioHora=" + honorarioHora + ", rubro=" + rubro
                + ", presentacion=" + presentacion + ", promedioPuntaje=" + promedioPuntaje + ", imagen=" + imagen
                + "]";
    }

}
