package com.example.demo.Repositorio;

import com.example.demo.Enumeraciones.Rol;
import com.example.demo.entidades.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Persona u WHERE u.email =:email")
    public Usuario BuscarUsuarioPorEmail(@Param("email") String email);

    @Query("SELECT u FROM Persona u WHERE u.rol= :rol")
    public List<Usuario> buscarUsuarios(@Param("rol") Rol rol);

}
