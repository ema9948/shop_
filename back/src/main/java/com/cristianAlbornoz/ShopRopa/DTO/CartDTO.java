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
public class CartDTO {

    private Integer id;
    @NotEmpty(message = "productoId empty")
    private Integer productoId;
    @NotEmpty(message = "productoNombre empty")
    private String productoNombre;
    @NotEmpty(message = "precio empty")
    private Double precio;
    @NotEmpty(message = "cantidad empty")
    private Integer cantidad;
    @NotEmpty(message = "monto empty")
    private Double monto;
    @NotEmpty(message = "productoColor empty")
    private String productoColor;
    @NotEmpty(message = "productoTalle empty")
    private String productoTalle;
}
