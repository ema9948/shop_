package com.cristianAlbornoz.ShopRopa.Config;

import com.cristianAlbornoz.ShopRopa.Utils.ExceptionsResponse.ResponseExGen;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllersExceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> ValidateErrorDTO(MethodArgumentNotValidException ex) {
        Map<String, String> response = extractMessageErrorsDTO(ex.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseExGen.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> ResponseExGen(ResponseExGen ex) {
        Map<String, String> response = new HashMap<>();
        response.put(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private Map<String, String> extractMessageErrorsDTO(BindingResult result) {
        Map<String, String> response = new HashMap<>();
        for (FieldError item : result.getFieldErrors()) {
            response.put(item.getField().toUpperCase(), item.getDefaultMessage().toUpperCase());
        }
        return response;
    }

}
