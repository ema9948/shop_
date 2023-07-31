package com.cristianAlbornoz.ShopRopa.Utils.ExceptionsResponse;

import lombok.Getter;

@Getter
public class ResponseExGen extends RuntimeException {
    private final String code;

    public ResponseExGen(String code, String message) {
        super(message);
        this.code = code;
    }
}
