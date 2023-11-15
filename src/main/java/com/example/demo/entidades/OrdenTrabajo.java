
package com.example.demo.entidades;

import com.example.demo.Enumeraciones.EstadoOrdenTrabajo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class OrdenTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rubro;
    private Integer puntaje;
    @Column(name = "valor")
    private double valor;

    @Enumerated(EnumType.STRING)
    private EstadoOrdenTrabajo estadOrden;
    @ManyToOne
    private Proveedor proveedor;
    @ManyToOne
    private Usuario usuario;
    private String comentario;
    private String comentarioCalificacion;

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(Long id, String rubro, Integer puntaje, double valor, EstadoOrdenTrabajo estadOrden,
            Proveedor proveedor, Usuario usuario, String comentario, String comentarioCalificacion) {
        this.id = id;
        this.rubro = rubro;
        this.puntaje = puntaje;
        this.valor = valor;
        this.estadOrden = estadOrden;
        this.proveedor = proveedor;
        this.usuario = usuario;
        this.comentario = comentario;
        this.comentarioCalificacion = comentarioCalificacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public EstadoOrdenTrabajo getEstadOrden() {
        return estadOrden;
    }

    public void setEstadOrden(EstadoOrdenTrabajo estadOrden) {
        this.estadOrden = estadOrden;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getComentarioCalificacion() {
        return comentarioCalificacion;
    }

    public void setComentarioCalificacion(String comentarioCalificacion) {
        this.comentarioCalificacion = comentarioCalificacion;
    }

    @Override
    public String toString() {
        return "OrdenTrabajo [id=" + id + ", rubro=" + rubro + ", puntaje=" + puntaje + ", valor=" + valor
                + ", estadOrden=" + estadOrden + ", proveedor=" + proveedor + ", usuario=" + usuario + ", comentario="
                + comentario + ", comentarioCalificacion=" + comentarioCalificacion + "]";
    }

}
