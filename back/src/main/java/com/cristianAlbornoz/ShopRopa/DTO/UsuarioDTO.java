package com.cristianAlbornoz.ShopRopa.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    @NotEmpty(message = "Email Empty")
    @Email(message = "Email not valid")
    private String email;
    @NotEmpty(message = "Password Empty")
    @Min(value = 7, message = "Password min 8 characters")
    private String password;
    private String rol;
    private String nombre;
    private String apellido;
    private String dni;
}
