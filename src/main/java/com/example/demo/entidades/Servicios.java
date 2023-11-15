package com.example.demo.entidades;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

public class Servicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private String nombre;
    // private String descripcion;
    @ManyToMany(mappedBy = "serviciosOfrecidos")
    private List<Proveedor> proveedores; // Proveedores que ofrecen este servicio
}
