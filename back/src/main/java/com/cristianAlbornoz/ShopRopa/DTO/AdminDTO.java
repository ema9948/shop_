package com.cristianAlbornoz.ShopRopa.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDTO {
    private Integer id;
    @NotEmpty(message = "nombre empty")
    private String nombre;
    @NotEmpty(message = "apellido empty")
    private String apellido;
    @NotEmpty(message = "dni empty")
    @Min(value = 7, message = "dni format  required 8 characters")
    @Max(value = 9, message = "dni format  required 8 characters")
    @Pattern(regexp = "\t^ \\ d {1,2} \\.? \\ d {3} \\.? \\ d {3} $")
    private String dni;
}
