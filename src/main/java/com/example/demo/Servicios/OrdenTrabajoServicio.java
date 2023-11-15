
package com.example.demo.Servicios;

import com.example.demo.Enumeraciones.EstadoOrdenTrabajo;
import com.example.demo.Repositorio.OrdenTrabajoRepositorio;
import com.example.demo.Repositorio.UsuarioRepositorio;
import com.example.demo.Repositorio.proveedorRepositorio;
import com.example.demo.entidades.OrdenTrabajo;
import com.example.demo.entidades.Proveedor;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenTrabajoServicio {

    @Autowired
    OrdenTrabajoRepositorio otRepositorio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    proveedorRepositorio proveedorRepositorio;

    @Autowired
    proveedorServicio proveedorServicio;

    @Transactional // crear orden de trabajo en estado cotizando para el contacto.
    public void crearOt(Long idProveedor, Long idUsuario, String comentario) {
        OrdenTrabajo ordentrabajo = new OrdenTrabajo();
        ordentrabajo.setProveedor(proveedorRepositorio.findById(idProveedor).get());
        ordentrabajo.setUsuario(usuarioRepositorio.findById(idUsuario).get());
        ordentrabajo.setEstadOrden(EstadoOrdenTrabajo.COTIZANDO);
        ordentrabajo.setComentario(comentario);// necesito pintar zzzz
        otRepositorio.save(ordentrabajo);
    }

    @Transactional // Servicio para asignar el valor por parte del proveedor
    public void asignarValor(Long idOrdenTrabajo, Long idProveedor, double valor) {
        Optional<OrdenTrabajo> respuesta = otRepositorio.findById(idOrdenTrabajo);

        if (respuesta.isPresent()) {
            OrdenTrabajo ordentrabajo = respuesta.get();
            ordentrabajo.setValor(valor);
            otRepositorio.save(ordentrabajo);
        }
    }

    @Transactional // El usuario acepta la cotizacion e inicia la orden
    public void aceptarOrdenTrabajo(Long idOrdenTrabajo) {
        Optional<OrdenTrabajo> respuesta = otRepositorio.findById(idOrdenTrabajo);

        if (respuesta.isPresent()) {

            OrdenTrabajo ordentrabajo = respuesta.get();
            ordentrabajo.setEstadOrden(EstadoOrdenTrabajo.ACTIVA);
            otRepositorio.save(ordentrabajo);
        }
    }

    @Transactional // El usuario cancela orden de servicio
    public void cancelarOrdenTrabajo(Long idOrdenTrabajo) {
        Optional<OrdenTrabajo> respuesta = otRepositorio.findById(idOrdenTrabajo);

        if (respuesta.isPresent()) {

            OrdenTrabajo ordentrabajo = respuesta.get();
            ordentrabajo.setEstadOrden(EstadoOrdenTrabajo.CANCELADA);
            otRepositorio.save(ordentrabajo);
        }
    }

    @Transactional // El proveedor confirma la finalizacion del servicio
    public void finalizarOrdenTrabajo(Long idOrdenTrabajo, Long idProveedor) {
        Optional<OrdenTrabajo> respuesta = otRepositorio.findById(idOrdenTrabajo);

        if (respuesta.isPresent()) {

            OrdenTrabajo ordentrabajo = respuesta.get();
            ordentrabajo.setEstadOrden(EstadoOrdenTrabajo.FINALIZADA);
            otRepositorio.save(ordentrabajo);
        }
    }

    @Transactional // El usuario califica la orden del servicio
    public void calificarOrdenTrabajo(Long idOrdenTrabajo, Long idUsuario, String comentarioCalificacion,
            Integer puntaje) {
        Optional<OrdenTrabajo> respuesta = otRepositorio.findById(idOrdenTrabajo);
        // agregar condicional si se califica solo si está finalizada
        if (respuesta.isPresent()) {

            OrdenTrabajo ordentrabajo = respuesta.get();
            ordentrabajo.setComentarioCalificacion(comentarioCalificacion);
            ordentrabajo.setPuntaje(puntaje);
            // ordentrabajo.getProveedor().setPuntaje
            otRepositorio.save(ordentrabajo);
        }
    }

    public List<OrdenTrabajo> ListarOrdenesTrabajoUsuario(Long idPersona) {

        List<OrdenTrabajo> ordenesTrabajo = new ArrayList<OrdenTrabajo>();
        ordenesTrabajo = otRepositorio.buscarPoridUsuario(idPersona);
        return ordenesTrabajo;
    }

    public List<OrdenTrabajo> ListarOrdenesTrabajoProveedor(Long idPersona) {

        List<OrdenTrabajo> ordenesTrabajo = new ArrayList<OrdenTrabajo>();
        ordenesTrabajo = otRepositorio.buscarPoridProveedor(idPersona);
        return ordenesTrabajo;
    }

    public List<OrdenTrabajo> ListarTodasOrdenesTrabajo() {

        List<OrdenTrabajo> ordenesTrabajo = new ArrayList<OrdenTrabajo>();
        ordenesTrabajo = otRepositorio.findAll();
        return ordenesTrabajo;
    }

    /*
     * @Transactional
     * public void calcularPromedioPuntajeProveedores() {
     * List<Proveedor> proveedores = proveedorServicio.listarProveedor();
     * 
     * for (Proveedor proveedor : proveedores) {
     * Long idProveedor = proveedor.getId();
     * List<OrdenTrabajo> ordenes = otRepositorio.buscarPoridProveedor(idProveedor);
     * double totalPuntaje = 0.0;
     * double totalCalificaciones = 0.0;
     * 
     * for (OrdenTrabajo orden : ordenes) {
     * 
     * if (orden.getPuntaje() != null) {
     * totalPuntaje += orden.getPuntaje();
     * totalCalificaciones++;
     * }
     * }
     * double promedioPuntaje = (totalCalificaciones > 0) ? totalPuntaje /
     * totalCalificaciones : 0.0;
     * 
     * // Actualiza el promedioPuntaje del proveedor
     * proveedor.setPromedioPuntaje(promedioPuntaje);
     * proveedorRepositorio.save(proveedor);
     * }
     * }
     */

    @Transactional // El admin edita el comentario
    public void editarComentario(Long idOrdenTrabajo, Long idUsuario, String comentarioCalificacion) {
        Optional<OrdenTrabajo> respuesta = otRepositorio.findById(idOrdenTrabajo);
        // agregar condicional si se califica solo si está finalizada
        if (respuesta.isPresent()) {

            OrdenTrabajo ordentrabajo = respuesta.get();
            ordentrabajo.setComentarioCalificacion(comentarioCalificacion);

            otRepositorio.save(ordentrabajo);
        }
    }

}
