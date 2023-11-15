package com.example.demo.Servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.Enumeraciones.Estado;
import com.example.demo.Enumeraciones.Rol;
import com.example.demo.Repositorio.UsuarioRepositorio;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Excepciones.MiException;

import com.example.demo.entidades.Usuario;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void crearUsuario(String nombre, String email, String password, String password2, Long telefono,
            String direccion)
            throws MiException {
        validar(nombre, email, password, password2, telefono, direccion);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setRol(Rol.USER);
        usuario.setEstado(Estado.ACTIVO);
        usuarioRepositorio.save(usuario);

    }

    @Transactional
    public void actualizar(Long id, String nombre, String email, String password,
            String password2,
            Long telefono, String direccion) throws MiException {

        validar(nombre, email, password, password2, telefono, direccion);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setDireccion(direccion);

            usuario.setRol(Rol.USER);
            // Estado indica que el usuario estara activo por defecto
            usuario.setEstado(Estado.ACTIVO);

            usuarioRepositorio.save(usuario);

        }
    }

    private void validar(String nombre, String email, String password, String password2, Long telefono,
            String direccion)
            throws MiException {

        if (nombre.isEmpty()) {
            throw new MiException("El nombre no puede estar vacio");
        }
        if (email.isEmpty()) {
            throw new MiException("El email no puede estar vacio");
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("El password no puede estar vacio, o ser nulo");
        }
        if (password.isEmpty()) {
            throw new MiException("El password no puede estar vacio");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        if (direccion.isEmpty()) {
            throw new MiException("la direccion no puede estar vacia");
        }

    }

    public List<Usuario> listarUsuarios(Rol rol) {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios = (List<Usuario>) usuarioRepositorio.buscarUsuarios(rol);
        return usuarios;
    }

    /*
     * @Override
     * public UserDetails loadUserByUsername(String email) throws
     * UsernameNotFoundException {
     * Persona persona = personaRepositorio.buscarPersonarPorEmail(email);
     * if (persona != null) {
     * List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();
     * GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" +
     * persona.getRol().toString());
     * permisos.add(p);
     * ServletRequestAttributes attr = (ServletRequestAttributes)
     * RequestContextHolder.currentRequestAttributes();
     * HttpSession session = attr.getRequest().getSession(true);
     * session.setAttribute("personasession", persona);
     * return new User(persona.getEmail(), persona.getPassword(), permisos);
     * } else {
     * return null;
     * }
     * 
     * }
     */

}