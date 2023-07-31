package com.cristianAlbornoz.ShopRopa.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
}
