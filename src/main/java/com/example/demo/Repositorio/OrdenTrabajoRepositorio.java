
package com.example.demo.Repositorio;

import com.example.demo.entidades.OrdenTrabajo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenTrabajoRepositorio extends JpaRepository<OrdenTrabajo, Long> {

    @Query("SELECT o FROM OrdenTrabajo o WHERE o.id =:id")
    public List<OrdenTrabajo> buscarPorid(@Param("id") Long id);

    @Query("SELECT o FROM OrdenTrabajo o WHERE o.usuario.id =:id")
    public List<OrdenTrabajo> buscarPoridUsuario(@Param("id") Long id);

    @Query("SELECT o FROM OrdenTrabajo o WHERE o.proveedor.id =:id")
    public List<OrdenTrabajo> buscarPoridProveedor(@Param("id") Long id);

}
