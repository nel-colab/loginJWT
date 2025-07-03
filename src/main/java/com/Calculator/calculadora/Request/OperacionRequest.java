package com.Calculator.calculadora.Request;

import lombok.Data;

@Data
public class OperacionRequest {
    private String expresion;
    private String resultado;
    private String tipoOperacion;
    private String username;
}
