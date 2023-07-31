package com.cristianAlbornoz.ShopRopa.controller.auth;

import com.cristianAlbornoz.ShopRopa.DTO.UsuarioDTO;
import com.cristianAlbornoz.ShopRopa.Utils.ExceptionsResponse.ResponseExGen;
import com.cristianAlbornoz.ShopRopa.Utils.ResponseAuth;
import com.cristianAlbornoz.ShopRopa.service.UsuarioService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("auth/")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("register")
    public ResponseEntity<ResponseAuth> saludo(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(usuarioService.usuarioSave(usuarioDTO), HttpStatus.CREATED);
    }

    @PostMapping("authenticate")
    public ResponseEntity<ResponseAuth> authentication(@Valid @RequestBody UsuarioDTO usuarioDTO) throws ResponseExGen {
        return new ResponseEntity<>(usuarioService.usuarioAuthentication(usuarioDTO), HttpStatus.ACCEPTED);
    }


    @PostMapping("check")
    public void success(
            HttpServletRequest request,
            @RequestParam() Map<String, String> data
    ) throws MPException, MPApiException {
        System.out.println(request.getQueryString());
        System.out.println("--------");


        System.out.println(data);
        MercadoPagoConfig.setAccessToken("APP_USR-2436658148363009-072618-1f51cd82e7c6d1ab2e9aa4e28213f121-739043136");
        PreferenceClient client = new PreferenceClient();

        String preferenceId = data.get("data.id");
        Preference preference = client.get(preferenceId);
        System.out.println(preference);
    }

}
