package com.cristianAlbornoz.ShopRopa.controller.adminControllers;


import com.cristianAlbornoz.ShopRopa.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("api/v1/admin/producto/")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @PostMapping("save")
    public ResponseEntity productoSave(String productoDTO, @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(productoService.productoSave(productoDTO, file), HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity productoAll() {
        return new ResponseEntity<>(productoService.allProducto(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity productoDelete(@PathVariable("id") Integer id) {
        return new ResponseEntity(productoService.productoDelete(id), HttpStatus.OK);
    }

    @GetMapping("producto/{img:.+}")
    public ResponseEntity<Resource> productoImg(@PathVariable String img) throws IOException {
        Resource file = productoService.getImg(img);
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }
}
