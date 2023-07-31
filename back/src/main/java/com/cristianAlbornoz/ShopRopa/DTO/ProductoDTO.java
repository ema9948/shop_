package com.cristianAlbornoz.ShopRopa.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDTO {
    private Integer id;
    @NotEmpty(message = "categoria empty")
    private String categoria;
    @NotEmpty(message = "nombre empty")
    private String nombre;
    @NotEmpty(message = "talle empty")
    private String talle;
    @NotEmpty(message = "valor empty")
    private Double precio;
    @NotEmpty(message = "color empty")
    private String color;
    @NotEmpty(message = "stock empty")
    private Integer stock;
    @NotEmpty(message = "descripcion empty")
    private String descripcion;
    private String img;

}
