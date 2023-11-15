package com.example.demo.Servicios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Enumeraciones.Estado;
import com.example.demo.Enumeraciones.Rol;
import com.example.demo.Excepciones.MiException;
import com.example.demo.Repositorio.OrdenTrabajoRepositorio;
import com.example.demo.Repositorio.proveedorRepositorio;
import com.example.demo.entidades.Imagen;
import com.example.demo.entidades.OrdenTrabajo;
import com.example.demo.entidades.Proveedor;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class proveedorServicio implements UserDetailsService {

    @Autowired
    private proveedorRepositorio proveedorRepositorio;

    @Autowired
    ImagenServicio imagenServicio;

    @Autowired
    private OrdenTrabajoRepositorio otRepositorio;

    @Transactional
    public void crearProveedor(MultipartFile archivo, String nombre, String email, String password, String password2,
            Long telefono,
            String direccion, float honorarioHoras, String rubro, String presentacion)
            throws MiException, IOException {

        validar(nombre, email, password, password2, telefono, direccion, honorarioHoras, rubro, presentacion, archivo);
        Proveedor proveedor = new Proveedor();

        proveedor.setNombre(nombre);
        proveedor.setEmail(email);
        proveedor.setRol(Rol.PROVEEDOR);
        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        proveedor.setTelefono(telefono);
        proveedor.setDireccion(direccion);
        proveedor.setHonorarioHora(honorarioHoras);
        proveedor.setRubro(rubro);
        proveedor.setPresentacion(presentacion);
        proveedor.setEstado(Estado.ACTIVO);

        Imagen imagen = imagenServicio.guardarImagenProveedor(archivo, proveedor);
        proveedor.setImagen(imagen);
        proveedor.setPromedioPuntaje(0.0);
        proveedorRepositorio.save(proveedor);
    }

    public List<Proveedor> listarProveedor() {

        List<Proveedor> listaProveedores = new ArrayList<Proveedor>();

        listaProveedores = proveedorRepositorio.findAll();
        return listaProveedores;
    }

    public List<Proveedor> listarProveedorPorRubro(String rubro) {

        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        proveedores = (List<Proveedor>) proveedorRepositorio.buscarProveedorPorRubro(rubro);
        return proveedores;
    }

    @Transactional
    public void actualizar(MultipartFile archivo, Long id, String nombre, String email, String password,
            String password2,
            Long telefono, String direccion, float honorarioHoras, String rubro, String presentacion)

            throws MiException {
        validar(nombre, email, password, password2, telefono, direccion, honorarioHoras, rubro, presentacion, archivo);

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            try {
                Proveedor proveedor = respuesta.get();
                proveedor.setNombre(nombre);
                proveedor.setEmail(email);
                proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
                proveedor.setDireccion(direccion);
                proveedor.setHonorarioHora(honorarioHoras);
                proveedor.setRubro(rubro);
                proveedor.setPresentacion(presentacion);
                proveedor.setRol(Rol.PROVEEDOR);
                Long idImagen = null;

                if (proveedor.getImagen() != null) {
                    idImagen = proveedor.getImagen().getId();
                }

                Imagen imagen = imagenServicio.modificarImagenProveedor(archivo, idImagen);

                proveedor.setImagen(imagen);

                proveedorRepositorio.save(proveedor);
            } catch (IOException ex) {
                System.out.println("No se encuentra la imagen");
            }
        }
    }

    private void validar(String nombre, String email, String password, String password2, Long telefono,
            String direccion, float honorarioHoras, String rubro, String presentacion, MultipartFile archivo)
            throws MiException {

        if (nombre.isEmpty()) {
            throw new MiException("El nombre no puede estar vacio");
        }
        if (email.isEmpty()) {
            throw new MiException("El email no puede estar vacio");
        }
        if (password.isEmpty()) {
            throw new MiException("El password no puede estar vacio");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        if (telefono == null) {
            throw new MiException("El telfono no puede ser nulo");
        }

        if (direccion.isEmpty()) {
            throw new MiException("La direccion no puede estar vacia");
        }
        if (honorarioHoras < 0) {
            throw new MiException("debes actualizar a un honorario por hora valido");
        }
        if (rubro.isEmpty()) {
            throw new MiException("El rubro no puede estar vacia");
        }
        if (presentacion.isEmpty()) {
            throw new MiException("La presentacion no puede estar vacia");
        }
        if (archivo == null) {
            throw new MiException("El archivo no puede ser nulo");
        }

    }

    public Proveedor buscarPorid(Long id) {
        Proveedor proveedor = proveedorRepositorio.findById(id).get();
        return proveedor;
    }

    public Proveedor BuscarPorId(String id) {
        return proveedorRepositorio.buscarPorNombreProveedor(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Proveedor proveedor = proveedorRepositorio.buscarProveedorPorEmail(email);
        if (proveedor != null) {
            List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + proveedor.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", proveedor);
            return new User(proveedor.getNombre(), proveedor.getPassword(), permisos);

        } else {
            return null;
        }
    }

    @Transactional
    public void calcularPromedioPuntajeProveedores(List<Proveedor> proveedores) {
        for (Proveedor proveedor : proveedores) {
            Long idProveedor = proveedor.getId();
            List<OrdenTrabajo> ordenes = otRepositorio.buscarPoridProveedor(idProveedor);
            double totalPuntaje = 0.0;
            double totalCalificaciones = 0.0;

            for (OrdenTrabajo orden : ordenes) {
                if (orden.getPuntaje() != null) {
                    totalPuntaje += orden.getPuntaje();
                    totalCalificaciones++;
                }
            }

            double promedioPuntaje = (totalCalificaciones > 0) ? (double) totalPuntaje / totalCalificaciones : 0.0;

            // Actualiza el promedioPuntaje del proveedor
            proveedor.setPromedioPuntaje(promedioPuntaje);
            proveedorRepositorio.save(proveedor);
        }
    }

}
