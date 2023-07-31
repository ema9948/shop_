package com.cristianAlbornoz.ShopRopa.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ClienteDireccion {

    @Id
    @Column(name = "cliente_id", nullable = false)
    private Integer id;
    private String provincia;
    private String localidad;
    private String cp;
    private String direccion;
    private String n_casa;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    @MapsId
    @JsonIgnore
    private Cliente cliente;
}
