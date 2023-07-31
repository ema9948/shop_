package com.cristianAlbornoz.ShopRopa.repository;

import com.cristianAlbornoz.ShopRopa.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    @Modifying
    @Query(value = "UPDATE `admin` SET `nombre`=:nombre,`apellido`=:apellido,`dni`=:dni WHERE `admin_id`=:id ", nativeQuery = true)
    void patch(@Param("nombre") String nombre, @Param("apellido") String apellido, @Param("dni") String dni, @Param("id") int id);
}
