package com.example.demo.controladores;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Excepciones.MiException;
import com.example.demo.Repositorio.proveedorRepositorio;
import com.example.demo.Servicios.proveedorServicio;
import com.example.demo.entidades.Persona;

import com.example.demo.entidades.Proveedor;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/proveedor") // localhost:8080/proveedor
public class proveedorControlador {

    @Autowired
    private proveedorServicio proveedorServicio;

    @Autowired
    private proveedorRepositorio proveedorRepositorio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        return "registroProveedor.html";
    }

    @PostMapping("/registrar")
    public String registro(String nombre, String email, @RequestParam String password,
            Long telefono, String password2,
            String direccion, float honorarioHoras, String rubro, String presentacion, ModelMap modelo,
            String rubroOtro, MultipartFile archivo) throws IOException, MiException {
        Proveedor prove = proveedorRepositorio.buscarProveedorPorEmail(email);

        if (prove != null && prove.getEmail().equals(email)) {

            modelo.addAttribute("emailDuplicado", true);
            return "registroProveedor.html";

        }
        try {

            // Crear el proveedor
            proveedorServicio.crearProveedor(archivo, nombre, email, password, password2, telefono, direccion,
                    honorarioHoras, rubro, presentacion);

            modelo.put("exito", "Usuario registrado correctamente !");
            String rubroSeleccionado = rubro;
            if (rubroSeleccionado.equals("Otro")) {
                rubroSeleccionado = rubroOtro;
            }
            return "redirect:/";

        } catch (MiException ex) {
            ex.printStackTrace();
            modelo.put("error", ex.getMessage());
            return "registroProveedor.html"; // Redirigir de nuevo al formulario de registro
        }
    }

    @GetMapping("/buscador")
    public String buscador() {
        List<Proveedor> proveedoress = proveedorServicio.listarProveedor();
        proveedorServicio.calcularPromedioPuntajeProveedores(proveedoress);

        return "Buscador";
    }

    @PostMapping("/buscador")

    public String buscador(@RequestParam String rubro, ModelMap modelo) {
        List<Proveedor> proveedores = proveedorRepositorio.buscarProveedorPorRubro(rubro);

        modelo.addAttribute("proveedores", proveedores);

        return "Buscador";
    }

    /*
     * @GetMapping("/proveedores")
     * public String listarProveedores(ModelMap modelo) {
     * List<Proveedor> proveedores = proveedorServicio.listarProveedor();
     * 
     * // Calcular el promedio de puntaje para los proveedores
     * proveedorServicio.calcularPromedioPuntajeProveedores(proveedores);
     * 
     * modelo.addAttribute("proveedores", proveedores);
     * return "ContactarProveedor.html";
     * }
     */

    @GetMapping("/buscador/{idproveedor}")
    public String buscador(@PathVariable Long idproveedor, ModelMap modelo) {

        // Proveedor pr = proveedorServicio.buscarPorid(idproveedor);
        // modelo.addAttribute("proveedor", proveedorServicio.buscarPorid(idproveedor));
        // modelo.addAttribute("imagen",
        // Base64.getEncoder().encodeToString(pr.getImagen().getContenido()));

        return "LoginUsuario";
    }

    @GetMapping("/panelProveedor")
    public String panelProveedor(HttpSession session, ModelMap modelo) {
        Persona proveedor = (Persona) session.getAttribute("personasession");
        modelo.addAttribute("imagen",
                Base64.getEncoder().encodeToString(((Proveedor) proveedor).getImagen().getContenido()));
        if (proveedor != null) {

            // Aquí tienes acceso al proveedor y sus datos
            modelo.addAttribute("proveedor", proveedor);
        }
        return "panelProveedor.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')") // ver si lo podemos sacar
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Proveedor logueado = proveedorRepositorio.buscarProveedorPorEmail(authentication.getName());
        modelo.put("proveedor", logueado);
        return "modificarProveedor.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizar(MultipartFile archivo, @PathVariable Long id, String nombre, String email,
            @RequestParam String password,
            @RequestParam String password2,
            Long telefono, String direccion, float honorarioHora, String rubro, String presentacion,
            ModelMap modelo) {

        try {
            proveedorServicio.actualizar(archivo, id, nombre, email, password, password2, telefono, direccion,
                    honorarioHora, rubro, presentacion);

            modelo.put("exito", "Proveedor actualizado correctamente!");

            return "redirect:/proveedor/panelProveedor";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "modificarProveedor.html";
        }

    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/index";
    }

    @GetMapping("/indiceProveedor")
    public String index() {

        return "index.html";
    }

    /*
     * @GetMapping("/proveedores")
     * public String listarProveedores(ModelMap modelo) {
     * List<Proveedor> proveedores = proveedorServicio.listarProveedor();
     * 
     * // Calcular el promedio de puntaje para los proveedores
     * proveedorServicio.calcularPromedioPuntajeProveedores(proveedores);
     * 
     * modelo.addAttribute("proveedores", proveedores);
     * return "ContactarProveedor.html";
     * }
     */

}