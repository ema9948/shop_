package com.cristianAlbornoz.ShopRopa.Utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseGen {
    private String message = "";
    private HttpStatus http;
    private Object data;
}
