package com.Calculator.calculadora.Response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OperationResponse {
    private Long id;
    private String expresion;
    private String resultado;
    private String tipoOperacion;
    private LocalDateTime fechaHora;
    private String username;
}
