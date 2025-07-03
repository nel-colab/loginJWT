package com.Calculator.calculadora.Service;

import com.Calculator.calculadora.Entity.UserEntity;
import com.Calculator.calculadora.Request.UserRequest;
import com.Calculator.calculadora.Response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserResponse registrarUsuario(UserRequest user);
    UserEntity findByUser(String username);
}
