package com.example.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Enumeraciones.Estado;
import com.example.demo.Excepciones.MiException;
import com.example.demo.Repositorio.ImagenRepositorio;
import com.example.demo.Repositorio.personaRepositorio;
import com.example.demo.Servicios.UsuarioServicio;
import com.example.demo.entidades.Persona;
import com.example.demo.entidades.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class portalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private personaRepositorio personaRepositorio;
    @Autowired
    ImagenRepositorio imagenRepositorio;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registroUsuario.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, @RequestParam Long telefono, @RequestParam String direccion,
            ModelMap modelo) {

        try {
            usuarioServicio.crearUsuario(nombre, email, password, password2, telefono, direccion);
            modelo.put("exito", "Usuario registrado correctamente!");

            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registroUsuario.html";
        }
    }

    @GetMapping("/panelUsuario")
    public String panelUsuario(HttpSession session, ModelMap modelo) {
        Persona persona = (Usuario) session.getAttribute("personasession");

        if (persona != null) {
            // Aquí tienes acceso al proveedor y sus datos
            modelo.addAttribute("persona", persona);
        } else {
            // Manejar la situación en la que el proveedor no está en la sesión
        }

        return "panelUsuario.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña incorrectos!");
        }
        // modelo.put("inactivo", "Usuario desactivado por un administrador");
        return "LoginUsuario.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PROVEEDOR')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {

        Persona logueado = (Persona) session.getAttribute("personasession");
        if (logueado.getEstado().equals(Estado.INACTIVO)) {
            System.out.println("inactivo");
            modelo.addAttribute("perfilInactivo", true);

            return "LoginUsuario.html";
        } else if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        } else if (logueado.getRol().toString().equals("PROVEEDOR")) {
            return "redirect:/proveedor/panelProveedor";
        } else if (logueado.getRol().toString().equals("USER")) {
            return "redirect:/buscador";
        }
        return "Buscador.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/buscador")
    public String inicio() {

        return "Buscador.html";
    }

    @GetMapping("/conocenos")
    public String conocenos() {

        return "conocenos.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')") // ver si lo podemos sacar
    @GetMapping("/perfilUsuario")
    public String perfilUsuario(ModelMap modelo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Persona logueado = personaRepositorio.buscarPersonarPorEmail(authentication.getName());
        modelo.put("usuario", logueado);
        return "modificarUsuario.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/perfilUsuario/{id}")
    public String actualizar(@PathVariable Long id, String nombre, String email,
            @RequestParam String password,
            @RequestParam String password2,
            Long telefono, String direccion, ModelMap modelo) {

        try {
            usuarioServicio.actualizar(id, nombre, email, password, password2, telefono, direccion);

            modelo.put("exito", "Usuario actualizado correctamente!");

            return "redirect:/buscador";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            System.out.println("No se esta modificando");
            return "modificarUsuario.html";
        }

    }

}
