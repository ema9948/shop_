package com.cristianAlbornoz.ShopRopa.repository;

import com.cristianAlbornoz.ShopRopa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String name);
}
