package com.cristianAlbornoz.ShopRopa.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productoId;
    private String productoNombre;
    private String productoColor;
    private String productoTalle;
    private Double precio;
    private Integer cantidad;
    private Double monto;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", productoId=" + productoId +
                ", productoName='" + productoNombre + '\'' +
                ", productoColor='" + productoColor + '\'' +
                ", productoTalle='" + productoTalle + '\'' +
                ", price=" + precio +
                ", quantity=" + cantidad +
                ", amount=" + monto +
                '}';
    }
}
