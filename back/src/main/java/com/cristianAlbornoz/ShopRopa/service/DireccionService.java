package com.cristianAlbornoz.ShopRopa.service;

import com.cristianAlbornoz.ShopRopa.DTO.ClienteDireccionDTO;
import com.cristianAlbornoz.ShopRopa.Utils.ExceptionsResponse.ResponseExGen;
import com.cristianAlbornoz.ShopRopa.Utils.Mapper;
import com.cristianAlbornoz.ShopRopa.Utils.ResponseGen;
import com.cristianAlbornoz.ShopRopa.model.ClienteDireccion;
import com.cristianAlbornoz.ShopRopa.model.Usuario;
import com.cristianAlbornoz.ShopRopa.repository.ClienteDireccionRepository;
import com.cristianAlbornoz.ShopRopa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DireccionService {
    private final ClienteDireccionRepository direccionRepository;
    private final UsuarioRepository usuarioRepository;
    private final Mapper mapper;

    public ResponseGen direccionAdd(ClienteDireccionDTO clienteDireccionDTO) {
        try {
            Usuario usuario = getPrincipal();

            ClienteDireccion direccion = mapper.clienteDireccion(clienteDireccionDTO);
            direccion.setCliente(usuario.getCliente());

            direccionRepository.save(direccion);
            return ResponseGen.builder().http(HttpStatus.CREATED).message("save").build();

        } catch (ResponseExGen e) {
            if (e.getCode().isBlank()) {
                throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
            throw new ResponseExGen(e.getCode(), e.getMessage());
        }
    }

    @Transactional
    public ResponseGen direccionPatch(ClienteDireccionDTO direccionDTO) {
        try {
            Usuario usuario = getPrincipal();
            ClienteDireccion clienteDireccion = direccionRepository.findById(usuario.getCliente().getId()).orElseThrow(() -> new ResponseExGen("404", "email not Found"));
            direccionRepository.clientePath(direccionDTO.getProvincia(), direccionDTO.getLocalidad(), direccionDTO.getCp(), direccionDTO.getDireccion(), direccionDTO.getN_casa(), clienteDireccion.getId());

            return ResponseGen.builder().message("update").build();
        } catch (ResponseExGen e) {
            if (e.getCode().isBlank()) {
                throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
            throw new ResponseExGen(e.getCode(), e.getMessage());
        }
    }

    private Usuario getPrincipal() {
        return usuarioRepository.findByEmail(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).orElseThrow(() -> new ResponseExGen("404", "Email not found"));
    }

}
