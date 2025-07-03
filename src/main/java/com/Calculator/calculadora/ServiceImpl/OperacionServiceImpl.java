package com.Calculator.calculadora.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Calculator.calculadora.Entity.OperacionEntity;
import com.Calculator.calculadora.Entity.UserEntity;
import com.Calculator.calculadora.Repository.OperacionRepository;
import com.Calculator.calculadora.Request.OperacionRequest;
import com.Calculator.calculadora.Response.OperationResponse;
import com.Calculator.calculadora.Service.OperacionService;

@Service
public class OperacionServiceImpl implements OperacionService{

    private final OperacionRepository operacionRepository;

    public OperacionServiceImpl(OperacionRepository operacionRepository) {
        this.operacionRepository = operacionRepository;
    }

    @Override
    public OperacionEntity guardarOperacion(OperacionRequest req, UserEntity usuario) {
        if (usuario == null) {
            throw new RuntimeException("Usuario es null");
        }

        OperacionEntity op = OperacionEntity.builder()
            .expresion(req.getExpresion())
            .resultado(req.getResultado())
            .tipoOperacion(req.getTipoOperacion())
            .fechaHora(LocalDateTime.now())
            .username(usuario.getUser()) // <-- aquí agregamos el string
                .user(usuario)               // <-- FK a UserEntity
            .build();

        return operacionRepository.save(op);
    }

    public List<OperationResponse> obtenerTodas() {
        return operacionRepository.findAll().stream().map(op -> {
            OperationResponse dto = new OperationResponse();
            dto.setId(op.getId());
            dto.setExpresion(op.getExpresion());
            dto.setResultado(op.getResultado());
            dto.setTipoOperacion(op.getTipoOperacion());
            dto.setFechaHora(op.getFechaHora());
            dto.setUsername(op.getUsername());
            return dto;
        }).toList();
    }
    
}
