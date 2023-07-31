package com.cristianAlbornoz.ShopRopa.controller.clienteControllers;

import com.cristianAlbornoz.ShopRopa.DTO.ClienteDireccionDTO;
import com.cristianAlbornoz.ShopRopa.Utils.ResponseGen;
import com.cristianAlbornoz.ShopRopa.service.DireccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cliente/direccion")
@RequiredArgsConstructor
public class ClienteController {
    private final DireccionService direccionService;

    @PostMapping("")
    public ResponseEntity<ResponseGen> clienteDireccionAdd(@RequestBody ClienteDireccionDTO ClienteDireccionDTO) {
        return new ResponseEntity<>(direccionService.direccionAdd(ClienteDireccionDTO), HttpStatus.CREATED);
    }

    @PatchMapping("")
    public ResponseEntity<ResponseGen> clienteDireccionPatch(@RequestBody ClienteDireccionDTO ClienteDireccionDTO) {
        return new ResponseEntity<>(direccionService.direccionPatch(ClienteDireccionDTO), HttpStatus.CREATED);
    }

}
