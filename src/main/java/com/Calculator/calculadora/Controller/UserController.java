package com.Calculator.calculadora.Controller;

import com.Calculator.calculadora.Config.JwtService;
import com.Calculator.calculadora.Entity.UserEntity;
import com.Calculator.calculadora.Request.UserRequest;
import com.Calculator.calculadora.Response.UserResponse;
import com.Calculator.calculadora.Request.LoginRequest;
import com.Calculator.calculadora.Service.UserService;
import com.Calculator.calculadora.Exception.UserAlreadyExistsException;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        UserResponse nuevoUsuario = userService.registrarUsuario(userRequest);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("El usuario ya está registrado, ingrese otro usuario");
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuario y devolver token JWT")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserEntity user = userService.findByUser(loginRequest.getUser());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(user.getUser());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    static class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }
        public String getToken() {
            return token;
        }
        public void setToken(String token) {
            this.token = token;
        }
    }
}

