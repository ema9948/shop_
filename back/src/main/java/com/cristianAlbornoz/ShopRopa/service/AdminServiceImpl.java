package com.cristianAlbornoz.ShopRopa.service;

import com.cristianAlbornoz.ShopRopa.DTO.AdminDTO;
import com.cristianAlbornoz.ShopRopa.Utils.ExceptionsResponse.ResponseExGen;
import com.cristianAlbornoz.ShopRopa.Utils.Mapper;
import com.cristianAlbornoz.ShopRopa.Utils.ResponseGen;
import com.cristianAlbornoz.ShopRopa.model.Usuario;
import com.cristianAlbornoz.ShopRopa.repository.AdminRepository;
import com.cristianAlbornoz.ShopRopa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UsuarioRepository usuarioRepository;
    private final AdminRepository adminRepository;
    private final Mapper mapper;


    public ResponseGen adminGet() {
        try {
            Usuario usuario = getPrincipal();
            return ResponseGen.builder()
                    .http(HttpStatus.OK)
                    .data(
                            new HashMap<>()
                                    .put(
                                            usuario.getEmail(),
                                            usuario.getAdmin()
                                    )
                    )
                    .build();

        } catch (ResponseExGen e) {
            if (e.getCode().isBlank()) {
                throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
            throw new ResponseExGen(e.getCode(), e.getMessage());
        }
    }

    public ResponseGen adminDelete() {
        try {
            Usuario usuario = getPrincipal();

            usuarioRepository.deleteById(usuario.getId());
            return ResponseGen.builder()
                    .http(HttpStatus.OK)
                    .data("delete")
                    .build();

        } catch (ResponseExGen e) {
            if (e.getCode().isBlank()) {
                throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
            throw new ResponseExGen(e.getCode(), e.getMessage());
        }
    }

    @Transactional
    public ResponseGen adminPatch(AdminDTO adminDTO) {
        try {
            Usuario usuario = getPrincipal();
            adminRepository.patch(adminDTO.getNombre(), adminDTO.getApellido(), adminDTO.getDni(), usuario.getId());
            return ResponseGen.builder().http(HttpStatus.OK).message("update").build();
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
