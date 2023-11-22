
package com.example.demo.controladores;

import com.example.demo.Repositorio.OrdenTrabajoRepositorio;
import com.example.demo.Servicios.ImagenServicio;
import com.example.demo.Servicios.OrdenTrabajoServicio;
import com.example.demo.Servicios.proveedorServicio;
import com.example.demo.entidades.OrdenTrabajo;
import com.example.demo.entidades.Persona;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import com.example.demo.entidades.Proveedor;
import java.util.Base64;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orden")
public class OrdenTrabajoControlador {

    @Autowired
    OrdenTrabajoServicio ots;

    @Autowired
    proveedorServicio ps;

    @Autowired
    OrdenTrabajoRepositorio otr;

    @Autowired
    ImagenServicio imagenServicio;

    @GetMapping("/contacto/{idproveedor}")
    public String contactar(@PathVariable Long idproveedor, ModelMap modelo) {
        Proveedor pr = ps.buscarPorid(idproveedor);
        modelo.addAttribute("proveedor", ps.buscarPorid(idproveedor));
        modelo.addAttribute("imagen", Base64.getEncoder().encodeToString(pr.getImagen().getContenido()));
        return "ContactarProveedor.html";
    }

    @PostMapping("/crearOrden")
    public String crearOrden(HttpSession session, @RequestParam Long idproveedor, @RequestParam String comentario) {
        Persona logueado = (Persona) session.getAttribute("personasession");
        Long idusuario = logueado.getId();
        ots.crearOt(idproveedor, idusuario, comentario);
        return "redirect:/orden/ordenes";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_PROVEEDOR')")
    @GetMapping("/ordenes")
    public String listarOrdenes(HttpSession session, ModelMap modelo) {
        Persona logueado = (Persona) session.getAttribute("personasession");
        Long idusuario = logueado.getId();
        System.out.println(logueado.getRol().toString());
        if (logueado.getRol().toString().equals("USER")) {
            System.out.println("entre en el user");
            List<OrdenTrabajo> listaOrdenes = ots.ListarOrdenesTrabajoUsuario(idusuario);

            modelo.addAttribute("listaOrdenes", listaOrdenes);

            return "MisOrdenesUsuario.html"; // Vista para usuarios
        } else if (logueado.getRol().toString().equals("PROVEEDOR")) {
            System.out.println("entre en el proveedor");
            List<OrdenTrabajo> listaOrdenesProveedor = ots.ListarOrdenesTrabajoProveedor(idusuario);

            modelo.addAttribute("listaOrdenes", listaOrdenesProveedor);

            return "MisOrdenes.html"; // Vista para proveedores
        }

        // Manejar cualquier otro caso si es necesario
        return "null";
    }

    @GetMapping("/cancelar/{id}")
    public String cancelarOrden(@PathVariable Long id) {
        ots.cancelarOrdenTrabajo(id);
        return "redirect:/orden/ordenes";
    }

    @GetMapping("/aceptar/{id}")
    public String aceptarOrden(@PathVariable Long id) {
        ots.aceptarOrdenTrabajo(id);
        return "redirect:/orden/ordenes";
    }

    @PostMapping("/aceptar/{id}")
    public String aceptarOrden(@PathVariable Long id, HttpSession session) {
        Persona logueado = (Persona) session.getAttribute("personasession");
        Long idusuario = logueado.getId();
        ots.aceptarOrdenTrabajo(id);
        return "redirect:/orden/ordenes";
    }

    @GetMapping("/calificar")
    public String calificarOrden(@PathVariable Long id) {
        // ots.aceptarOrdenTrabajo(id);
        return "redirect:/orden/ordenes";
    }

    @PostMapping("/calificar/{id}")
    public String calificarOrden(@PathVariable Long id, HttpSession session, @RequestParam Integer puntaje,
            String comentarioCalificacion) {
        Persona logueado = (Persona) session.getAttribute("personasession");
        Long idusuario = logueado.getId();
        ots.finalizarOrdenTrabajo(id, idusuario);
        ots.calificarOrdenTrabajo(id, idusuario, comentarioCalificacion, puntaje);

        return "redirect:/orden/ordenes";
    }

    @PostMapping("/cotizacion/{id}")
    public String cotizar(@PathVariable Long id, @RequestParam double valor, HttpSession session) {
        Persona logueado = (Persona) session.getAttribute("personasession");
        Long idusuario = logueado.getId();
        ots.asignarValor(id, idusuario, valor);

        return "redirect:/orden/ordenes";
    }

    @GetMapping("/finalizar/{id}")
    public String finalizarOrden(@PathVariable Long id, HttpSession session) {
        Persona logueado = (Persona) session.getAttribute("personasession");
        Long idusuario = logueado.getId();
        ots.finalizarOrdenTrabajo(id, idusuario);

        return "redirect:/orden/ordenes";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/editarComentario/{id}")
    public String editarComentario(@PathVariable Long id, HttpSession session, String comentarioCalificacion) {
        Persona logueado = (Persona) session.getAttribute("personasession");
        Long idusuario = logueado.getId();
        ots.editarComentario(id, idusuario, comentarioCalificacion);

        return "redirect:/admin/dashboard";
    }

}
