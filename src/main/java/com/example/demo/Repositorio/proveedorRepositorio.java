package com.example.demo.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Proveedor;

@Repository
public interface proveedorRepositorio extends JpaRepository<Proveedor, Long> {

    @Query("SELECT u FROM Persona u WHERE u.email =:email")
    public Proveedor buscarProveedorPorEmail(@Param("email") String email);

    @Query("SELECT p FROM Persona p WHERE p.rubro =:rubro")
    public List<Proveedor> buscarProveedorPorRubro(@Param("rubro") String rubro);

    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    public Proveedor buscarPorNombreProveedor(@Param("nombre") String nombre);

    @Query("SELECT p FROM Persona p WHERE p.rubro =:id")
    public List<Proveedor> buscarProveedorPorId(@Param("id") Long id);

    @Query("SELECT u FROM Persona u WHERE u.email =:email")
    public boolean existsByEmail(@Param("email") String email);

}
