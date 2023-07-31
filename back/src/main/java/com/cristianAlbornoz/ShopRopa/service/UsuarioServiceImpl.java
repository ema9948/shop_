package com.cristianAlbornoz.ShopRopa.service;


import com.cristianAlbornoz.ShopRopa.DTO.UsuarioDTO;
import com.cristianAlbornoz.ShopRopa.JWT.JWTService;
import com.cristianAlbornoz.ShopRopa.Utils.ExceptionsResponse.ResponseExGen;
import com.cristianAlbornoz.ShopRopa.Utils.Mapper;
import com.cristianAlbornoz.ShopRopa.Utils.ResponseAuth;
import com.cristianAlbornoz.ShopRopa.model.Admin;
import com.cristianAlbornoz.ShopRopa.model.Cliente;
import com.cristianAlbornoz.ShopRopa.model.Usuario;
import com.cristianAlbornoz.ShopRopa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final Mapper mapper;

    public ResponseAuth usuarioSave(UsuarioDTO usuarioDTO) {
        try {
            if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent())
                throw new ResponseExGen("409", "Email en uso.");

            Usuario usuario = mapper.usuario(usuarioDTO, encoder);
            generateUserRol(usuarioDTO, usuario);
            usuarioRepository.save(usuario);

            return ResponseAuth.builder().token(jwtService.generateToken(usuario)).build();
        } catch (ResponseExGen e) {
            if (e.getCode().isBlank()) {
                throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
            throw new ResponseExGen(e.getCode(), e.getMessage());
        }
    }

    public ResponseAuth usuarioAuthentication(UsuarioDTO usuarioDTO) {
        try {

            Usuario usuario = mapper.usuarioAuth(usuarioDTO);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    usuario.getEmail(),
                    usuario.getPassword()
            ));

            return ResponseAuth.builder().token(jwtService.generateToken(usuario)).build();
        } catch (ResponseExGen e) {
            if (e.getCode().isBlank()) {
                throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
            throw new ResponseExGen(e.getCode(), e.getMessage());
        }
    }

    public ResponseAuth usuarioPatch(UsuarioDTO usuarioDTO) {
        try {

        } catch (RuntimeException e) {

        }
        return null;
    }

    private void generateUserRol(UsuarioDTO usuarioDTO, Usuario usuario) {
        if (usuarioDTO.getRol().equalsIgnoreCase("ADMIN")) {
            Admin admin = Admin.builder()
                    .dni(usuarioDTO.getDni())
                    .apellido(usuarioDTO.getApellido())
                    .nombre(usuarioDTO.getNombre())
                    .build();

            usuario.setAdmin(admin);
            admin.setUsuario(usuario);
        } else if (usuarioDTO.getRol().equalsIgnoreCase("CLIENTE")) {
            Cliente cliente = Cliente
                    .builder()
                    .dni(usuarioDTO.getDni())
                    .apellido(usuarioDTO.getApellido())
                    .nombre(usuarioDTO.getNombre())
                    .build();
            usuario.setCliente(cliente);
            cliente.setUsuario(usuario);
        }
    }
}
