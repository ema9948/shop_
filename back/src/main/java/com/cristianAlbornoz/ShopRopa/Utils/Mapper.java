package com.cristianAlbornoz.ShopRopa.Utils;

import com.cristianAlbornoz.ShopRopa.DTO.*;
import com.cristianAlbornoz.ShopRopa.model.*;
import com.mercadopago.client.preference.PreferenceItemRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public Usuario usuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder().email(usuarioDTO.getEmail()).rol(usuarioDTO.getRol().toUpperCase()).password(usuarioDTO.getPassword()).build();
    }

    public Usuario usuarioAuth(UsuarioDTO usuarioDTO) {
        return Usuario.builder().email(usuarioDTO.getEmail()).password(usuarioDTO.getPassword()).build();

    }

    public Usuario usuario(UsuarioDTO usuarioDTO, PasswordEncoder encoder) {
        return Usuario.builder().email(usuarioDTO.getEmail()).rol(usuarioDTO.getRol().toUpperCase()).password((encoder.encode(usuarioDTO.getPassword()))).build();
    }

    public UsuarioDTO usuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder().email(usuario.getEmail()).rol(usuario.getRol()).password(usuario.getPassword()).build();
    }

    public Admin admin(AdminDTO adminDTO) {
        return Admin.builder().dni(adminDTO.getDni()).nombre(adminDTO.getNombre()).apellido(adminDTO.getApellido()).build();
    }

    public AdminDTO adminDTO(Admin admin) {
        return AdminDTO.builder().id(admin.getId()).dni(admin.getDni()).apellido(admin.getApellido()).nombre(admin.getNombre()).build();
    }


    public Producto producto(ProductoDTO pro) {
        return Producto.builder()
                .id(pro.getId())
                .nombre(pro.getNombre())
                .color(pro.getColor())
                .stock(pro.getStock())
                .talle(pro.getTalle())
                .img(pro.getImg())
                .precio(pro.getPrecio())
                .categoria(pro.getCategoria())
                .descripcion(pro.getDescripcion())
                .categoria(pro.getCategoria())
                .build();
    }

    public ProductoDTO productoDTO(Producto pro) {
        return ProductoDTO.builder()
                .id(pro.getId())
                .nombre(pro.getNombre())
                .color(pro.getColor())
                .stock(pro.getStock())
                .talle(pro.getTalle())
                .img(pro.getImg())
                .precio(pro.getPrecio())
                .categoria(pro.getCategoria())
                .descripcion(pro.getDescripcion())
                .categoria(pro.getCategoria())
                .build();
    }

    public Orden orden(OrdenDTO ordenDTO) {
        List<Cart> carts = new ArrayList<>();
        for (CartDTO cart : ordenDTO.getCartList()) {
            carts.add(cart(cart));
        }

        return Orden.builder()
                .id(ordenDTO.getId())
                .date(ordenDTO.getDate())
                .montoTotal(ordenDTO.getMontoTotal())
                .cartList(carts)
                .build();
    }

    public OrdenDTO ordenDTO(Orden orden) {
        List<CartDTO> carts = new ArrayList<>();
        for (Cart cart : orden.getCartList()) {
            carts.add(cartDTO(cart));
        }

        return OrdenDTO.builder()
                .id(orden.getId())
                .cartList(carts)
                .montoTotal(orden.getMontoTotal())
                .date(orden.getDate())
                .build();
    }


    public Cart cart(CartDTO cartDTO) {
        return Cart.builder()
                .id(cartDTO.getId())
                .productoId(cartDTO.getProductoId())
                .precio(cartDTO.getPrecio())
                .productoTalle(cartDTO.getProductoTalle())
                .productoColor(cartDTO.getProductoColor())
                .productoNombre(cartDTO.getProductoNombre())
                .monto(cartDTO.getMonto())
                .cantidad(cartDTO.getCantidad())
                .build();
    }

    public CartDTO cartDTO(Cart cart) {
        return CartDTO.builder()
                .id(cart.getId())
                .productoColor(cart.getProductoColor())
                .productoTalle(cart.getProductoTalle())
                .productoId(cart.getProductoId())
                .precio(cart.getPrecio())
                .productoNombre(cart.getProductoNombre())
                .monto(cart.getMonto())
                .cantidad(cart.getCantidad())
                .build();
    }

    public PreferenceItemRequest PreferenceItemRequest(Cart cart) {

        return PreferenceItemRequest.builder()
                .id(cart.getProductoId().toString())
                .title(cart.getProductoNombre())
                .pictureUrl("http://www.myapp.com/myimage.jpg")
                .description("Producto  " + cart.getProductoNombre() + " , color " + cart.getProductoColor() + " y talle " + cart.getProductoTalle())
                .quantity(cart.getCantidad())
                .unitPrice(BigDecimal.valueOf(cart.getPrecio()))
                .build();
    }

    public ClienteDireccion clienteDireccion(ClienteDireccionDTO direccionDTO) {
        return ClienteDireccion.builder()
                .direccion(direccionDTO.getDireccion())
                .cp(direccionDTO.getCp())
                .n_casa(direccionDTO.getN_casa())
                .localidad(direccionDTO.getLocalidad())
                .provincia(direccionDTO.getProvincia())
                .build();
    }
}
