package com.cristianAlbornoz.ShopRopa.service;

import com.cristianAlbornoz.ShopRopa.DTO.CartDTO;
import com.cristianAlbornoz.ShopRopa.DTO.OrdenDTO;
import com.cristianAlbornoz.ShopRopa.Utils.ExceptionsResponse.ResponseExGen;
import com.cristianAlbornoz.ShopRopa.Utils.Mapper;
import com.cristianAlbornoz.ShopRopa.Utils.ResponseGen;
import com.cristianAlbornoz.ShopRopa.model.Cart;
import com.cristianAlbornoz.ShopRopa.model.Orden;
import com.cristianAlbornoz.ShopRopa.model.Producto;
import com.cristianAlbornoz.ShopRopa.model.Usuario;
import com.cristianAlbornoz.ShopRopa.repository.OrdenRepository;
import com.cristianAlbornoz.ShopRopa.repository.ProductoRepository;
import com.cristianAlbornoz.ShopRopa.repository.UsuarioRepository;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdenRepository ordenRepository;
    private final ProductoRepository productoRepository;
    private final Mapper mapper;
    private final UsuarioRepository usuarioRepository;

    public ResponseGen ordenSave(OrdenDTO ordenDTO) {
        try {

            Usuario usuario = getPrincipal();

            MercadoPagoConfig.setAccessToken("APP_USR-2436658148363009-072618-1f51cd82e7c6d1ab2e9aa4e28213f121-739043136");

            List<Producto> productos = productoRepository.findAllById(extractIdCart(ordenDTO));
            cartCheckProducto(productos, ordenDTO);
            Orden orden = mapper.orden(ordenDTO);


            PreferenceClient client = new PreferenceClient();

            List<PreferenceItemRequest> items = new ArrayList<>();
            PreferenceItemRequest item =
                    PreferenceItemRequest.builder()
                            .title("Dummy Title")
                            .description("Dummy description")
                            .pictureUrl("http://www.myapp.com/myimage.jpg")
                            .quantity(1)
                            .unitPrice(new BigDecimal("10"))
                            .build();
            items.add(item);
            PreferenceBackUrlsRequest backUrls =
// ...
                    PreferenceBackUrlsRequest.builder()
                            .success("https://www.seu-site/success")
                            .pending("https://www.seu-site/pending")
                            .failure("https://www.seu-site/failure")
                            .build();


            PreferenceRequest request = PreferenceRequest.builder().items(items).notificationUrl("https://0e16-181-192-0-162.ngrok.io/auth/check?email=" + usuario.getEmail()).build();

            Preference pre = client.create(request);
            System.out.println(pre.getInitPoint());
            System.out.println(pre.getSandboxInitPoint());
            //   orden.setMontoTotal(amountTotal(orden.getCartList()));
            //     orden.setCliente(usuario.getCliente());

            //   ordenRepository.save(orden);

            return ResponseGen.builder().http(HttpStatus.CREATED).build();
        } catch (ResponseExGen ex) {
            if (ex.getCode().isBlank()) {
                throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
            throw new ResponseExGen(ex.getCode(), ex.getMessage());
        } catch (MPApiException ex) {
            System.out.printf(
                    "MercadoPago Error. Status: %s, Content: %s%n",
                    ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
        } catch (MPException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ResponseGen ordenAll() {
        try {
            Usuario usuario = getPrincipal();
            
            List<Orden> orden = ordenRepository.findAll().stream().
                    filter(item -> item.getCliente().getId().equals(usuario.getCliente().getId()))
                    .collect(Collectors.toList());

            return ResponseGen.builder().data(ordenDTOList(orden)).build();
        } catch (ResponseExGen e) {
            if (e.getCode().isBlank()) {
                throw new ResponseExGen("500", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
            throw new ResponseExGen(e.getCode(), e.getMessage());
        }
    }

    // methods utils
    private static List<Integer> extractIdCart(OrdenDTO ordenDTO) {
        return ordenDTO.getCartList().stream().map(CartDTO::getProductoId).collect(Collectors.toList());
    }

    private static void cartCheckProducto(List<Producto> productos, OrdenDTO ordenDTO) {
        int i = 0;
        for (Producto producto : productos) {
            CartDTO cart = ordenDTO.getCartList().get(i);

            if (!Objects.equals(producto.getId(), cart.getProductoId()))
                throw new ResponseExGen("409", cart.getId() + " de producto incorrecto");


            if (!Objects.equals(producto.getPrecio(), cart.getPrecio()))
                throw new ResponseExGen("409", producto.getNombre() + " precio incorrecto ");

            if ((cart.getCantidad() * producto.getPrecio()) != cart.getMonto())
                throw new ResponseExGen("409", cart.getProductoNombre() + " Monto incorrecto");

            if (producto.getStock() < 1)
                throw new ResponseExGen("409", "Stock de " + producto.getNombre() + " menor a 1 ");

            if (producto.getStock() < cart.getCantidad())
                throw new ResponseExGen("409", "Stock " + producto.getNombre() + " insuficiente ");
            i++;
        }

    }


    private static Double amountTotal(List<Cart> carts) {
        Double amountTotal = 0.0;
        for (Cart cart : carts) {
            amountTotal += cart.getMonto();
        }

        return amountTotal;
    }

    private List<OrdenDTO> ordenDTOList(List<Orden> ordenStream) {
        List<OrdenDTO> ordenDTOS = new ArrayList<>();
        for (Orden order : ordenStream) {
            ordenDTOS.add(mapper.ordenDTO(order));
        }
        return ordenDTOS;
    }


    private Usuario getPrincipal() {
        return usuarioRepository.findByEmail(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).orElseThrow(() -> new ResponseExGen("404", "Email not found"));
    }

}
