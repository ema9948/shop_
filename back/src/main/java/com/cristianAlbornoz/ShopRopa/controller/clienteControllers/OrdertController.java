package com.cristianAlbornoz.ShopRopa.controller.clienteControllers;

import com.cristianAlbornoz.ShopRopa.DTO.OrdenDTO;
import com.cristianAlbornoz.ShopRopa.Utils.ResponseGen;
import com.cristianAlbornoz.ShopRopa.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cliente/order/")
@RequiredArgsConstructor
public class OrdertController {
    private final OrderService orderService;

    @PostMapping("save")
    public ResponseEntity<ResponseGen> ordenSave(@RequestBody OrdenDTO ordenDTO) {
        return new ResponseEntity<>(orderService.ordenSave(ordenDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGen> ordenGet(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(orderService.ordenAll(), HttpStatus.OK);
    }
}
