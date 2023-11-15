package com.example.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Servicios.proveedorServicio;
import com.example.demo.entidades.Proveedor;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    proveedorServicio proveedorServicio;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenProveedor(@PathVariable String id) {
        Proveedor proveedor = proveedorServicio.BuscarPorId(id);
        byte[] imagen = proveedor.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        System.out.println("Este es el proveedor" + proveedor);

        return new ResponseEntity<byte[]>(imagen, headers, HttpStatus.OK);

    }

}