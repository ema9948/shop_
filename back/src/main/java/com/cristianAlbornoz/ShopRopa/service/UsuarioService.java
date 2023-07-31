package com.cristianAlbornoz.ShopRopa.service;

import com.cristianAlbornoz.ShopRopa.DTO.UsuarioDTO;
import com.cristianAlbornoz.ShopRopa.Utils.ResponseAuth;

public interface UsuarioService {

    ResponseAuth usuarioSave(UsuarioDTO usuario);

    ResponseAuth usuarioAuthentication(UsuarioDTO usuarioDTO);

    ResponseAuth usuarioPatch(UsuarioDTO usuarioDTO);
}
