package com.example.demo.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Persona;

@Repository
public interface personaRepositorio extends JpaRepository<Persona, Long> {

    @Query("SELECT u FROM Persona u WHERE u.email =:email")
    public Persona buscarPersonarPorEmail(@Param("email") String email);

    @Query("SELECT u FROM Persona u WHERE u.nombre =:nombre")
    public Persona buscarPorNombrePersona(@Param("nombre") String nombre);

    @Query("SELECT u FROM Usuario u WHERE u.email= :email")
    public Persona buscarPorEmail(@Param("email") String email);

}
