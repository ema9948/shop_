package com.cristianAlbornoz.ShopRopa.repository;

import com.cristianAlbornoz.ShopRopa.model.ClienteDireccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteDireccionRepository extends JpaRepository<ClienteDireccion, Integer> {

    @Modifying
    @Query(value = "UPDATE `cliente_direcion` Set `provincia`=:provincia, `localidad`=:localidad, `cp`=:cp, `direccion`=:direccion,`n_casa`=:n_casa WHERE `cliente_direccion`.`cliente_id`=:id", nativeQuery = true)
    void clientePath(@Param("provincia") String provincia, @Param("localidad") String localidad, @Param("cp") String cp, @Param("direccion") String direccion, @Param("n_casa") String n_casa, @Param("id") Integer id);
}
