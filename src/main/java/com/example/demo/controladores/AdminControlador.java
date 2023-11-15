package com.example.demo.controladores;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Enumeraciones.Rol;
import com.example.demo.Servicios.OrdenTrabajoServicio;
import com.example.demo.Servicios.ServicioPersona;
import com.example.demo.Servicios.UsuarioServicio;
import com.example.demo.Servicios.proveedorServicio;
import com.example.demo.entidades.OrdenTrabajo;
import com.example.demo.entidades.Proveedor;
import com.example.demo.entidades.Usuario;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    proveedorServicio proveedorServicio;

    @Autowired
    ServicioPersona servicioPersona;

    @Autowired
    OrdenTrabajoServicio ordenTrabajoServicio;

    @GetMapping("/dashboard")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios(Rol.USER);
        modelo.addAttribute("usuarios", usuarios);

        List<Proveedor> proveedores = proveedorServicio.listarProveedor();
        modelo.addAttribute("proveedores", proveedores);

        List<OrdenTrabajo> listaOt = ordenTrabajoServicio.ListarTodasOrdenesTrabajo();
        modelo.addAttribute("listaOt", listaOt);

        return "panelAdmin.html";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id) {

        return null;
    }

    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Long id) {
        servicioPersona.cambiarEstado(id);
        return "redirect:/admin/dashboard";
    }

}
