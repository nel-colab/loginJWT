package com.Calculator.calculadora.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class UserResponse {

    private String user;
    private String nombre;
    private String email;

}
