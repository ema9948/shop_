package com.cristianAlbornoz.ShopRopa.service;

import com.cristianAlbornoz.ShopRopa.DTO.ProductoDTO;
import com.cristianAlbornoz.ShopRopa.Utils.ExceptionsResponse.ResponseExGen;
import com.cristianAlbornoz.ShopRopa.Utils.Mapper;
import com.cristianAlbornoz.ShopRopa.Utils.ResponseGen;
import com.cristianAlbornoz.ShopRopa.controller.adminControllers.ProductoController;
import com.cristianAlbornoz.ShopRopa.model.Producto;
import com.cristianAlbornoz.ShopRopa.model.Usuario;
import com.cristianAlbornoz.ShopRopa.repository.ProductoRepository;
import com.cristianAlbornoz.ShopRopa.repository.UsuarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final Mapper mapper;

    private final static Path root = Paths.get("uploads");

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException exGen) {
            throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public ResponseGen productoSave(String productoDTOJson, MultipartFile file) {
        try {
            Usuario usuario = getPrincipal();
            Producto producto = new ObjectMapper().readValue(productoDTOJson, Producto.class);
            producto.setImg(urlImg(file));
            producto.setAdmin(usuario.getAdmin());
            productoRepository.save(producto);

            return ResponseGen.builder().message("save").build();
        } catch (ResponseExGen e) {
            if (e.getCode().isBlank()) {
                throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
            throw new ResponseExGen(e.getCode(), e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public ResponseGen productoPatch(ProductoDTO productoDTO, MultipartFile file) {
        try {

            Usuario usuario = getPrincipal();
            Producto producto = productoRepository.findById(productoDTO.getId()).orElseThrow(() -> new ResponseExGen("404", "Producto not found."));
            if (usuario.getAdmin().getId().equals(producto.getAdmin().getId()))
                throw new ResponseExGen("409", HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

            if (file.isEmpty()) {
                productoRepository.patch(
                        productoDTO.getCategoria(),
                        productoDTO.getColor(),
                        productoDTO.getDescripcion(),
                        productoDTO.getNombre(),
                        productoDTO.getStock(),
                        productoDTO.getTalle(),
                        productoDTO.getPrecio(),
                        producto.getImg(),
                        producto.getId()
                );
                return ResponseGen.builder().message("update").build();
            }

            productoRepository.patch(
                    productoDTO.getCategoria(),
                    productoDTO.getColor(),
                    productoDTO.getDescripcion(),
                    productoDTO.getNombre(),
                    productoDTO.getStock(),
                    productoDTO.getTalle(),
                    productoDTO.getPrecio(),
                    urlImg(file),
                    producto.getId()
            );

            return ResponseGen.builder().message("save").build();

        } catch (ResponseExGen e) {
            if (e.getCode().isBlank()) {
                throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
            throw new ResponseExGen(e.getCode(), e.getMessage());
        }
    }


    public ResponseGen allProducto() {
        try {

            List<Producto> productosAll = productoRepository.findAll();

            List<ProductoDTO> productos = new ArrayList<>();

            for (Producto producto : productosAll) {
                productos.add(mapper.productoDTO(producto));
            }

            return ResponseGen.builder().data(productos).build();
        } catch (ResponseExGen e) {
            if (e.getCode().isBlank()) {
                throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
            throw new ResponseExGen(e.getCode(), e.getMessage());
        }
    }

    public ResponseGen productoDelete(Integer id) {
        try {
            productoRepository.deleteById(id);
            return ResponseGen.builder().http(HttpStatus.OK).build();
        } catch (ResponseExGen e) {
            if (e.getCode().isBlank()) {
                throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
            throw new ResponseExGen(e.getCode(), e.getMessage());
        }
    }

    public Resource getImg(String img) {
        try {
            Path file = root.resolve(img);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {

                return resource;
            } else {
                throw new ResponseExGen("", "Img read fail" + img);
            }


        } catch (ResponseExGen | MalformedURLException e) {
            throw new ResponseExGen("500", e.getMessage());
        }
    }


    private String urlImg(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String uuidImg = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
                Files.copy(file.getInputStream(), root.resolve(uuidImg));
                return MvcUriComponentsBuilder.fromMethodName(ProductoController.class, "productoImg", uuidImg).build().toString();
            }
            return "";
        } catch (IOException e) {
            throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }

    private Usuario getPrincipal() {
        return usuarioRepository.findByEmail(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).orElseThrow(() -> new ResponseExGen("404", "Email not found"));
    }


}
