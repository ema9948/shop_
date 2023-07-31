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
public class ClienteDireccionDTO {
    private Integer id;
    @NotEmpty(message = "provincia empty")
    private String provincia;
    @NotEmpty(message = "localidad empty")
    private String localidad;
    @NotEmpty(message = "cp empty")
    private String cp;
    @NotEmpty(message = "direccion empty")
    private String direccion;
    @NotEmpty(message = "n_casa empty")
    private String n_casa;

}
