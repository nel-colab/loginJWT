package com.Calculator.calculadora.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.Calculator.calculadora.Entity.OperacionEntity;
import com.Calculator.calculadora.Entity.UserEntity;
import com.Calculator.calculadora.Request.OperacionRequest;
import com.Calculator.calculadora.Response.OperationResponse;
import com.Calculator.calculadora.Service.OperacionService;
import com.Calculator.calculadora.Service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private final OperacionService operacionService;
    private final UserService userService;

    @Autowired
    public CalculatorController(OperacionService operacionService,
                                UserService userService) {
        this.operacionService = operacionService;
        this.userService = userService;
    }

    @PostMapping("/registrarOperacion")
    @Operation(summary = "Registro de operaciones")
    public ResponseEntity<OperacionEntity> registrarOperacion(
            @RequestBody OperacionRequest req) {          // <-- usar DTO

        // 1. Extraer el username del JWT
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // 2. Cargar el UserEntity asociado
        UserEntity usuario = userService.findByUser(username);
        if (usuario == null) {
            return ResponseEntity.status(401).build();
        }

        // 3. Delegar al servicio, que recibe el DTO + usuario
        OperacionEntity guardada = operacionService.guardarOperacion(req, usuario);

        // 4. Devolver la entidad persistida
        return ResponseEntity.ok(guardada);
    }

    @GetMapping("/listOperaciones")
    @Operation(summary = "Listado de operaciones")
    public ResponseEntity<List<OperationResponse>> listarOperaciones() {
        return ResponseEntity.ok(operacionService.obtenerTodas());
    }
}
