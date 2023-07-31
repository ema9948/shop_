package com.cristianAlbornoz.ShopRopa.repository;

import com.cristianAlbornoz.ShopRopa.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    Optional<Producto> findByImg(String img);

    @Modifying
    @Query(value = "UPDATE `producto` SET `categoria`=:categoria, `color`=:color, `descripcion`=:descripcion, `nombre`=:nombre,`stock`=:stock,`talle`=:talle,`precio`=:precio,`img`=:img WHERE `id`=:id", nativeQuery = true)
    void patch(@Param("categoria") String categoria, @Param("color") String color, @Param("descripcion") String descripcion, @Param("nombre") String nombre, @Param("stock") Integer stock, @Param("talle") String talle, @Param("precio") Double precio, @Param("img") String img, @Param("id") Integer id);
}
