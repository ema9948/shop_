package com.cristianAlbornoz.ShopRopa.repository;

import com.cristianAlbornoz.ShopRopa.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
