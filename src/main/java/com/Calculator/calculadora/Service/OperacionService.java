    package com.Calculator.calculadora.Service;

    import java.util.List;

    import org.springframework.stereotype.Service;

    import com.Calculator.calculadora.Entity.OperacionEntity;
    import com.Calculator.calculadora.Entity.UserEntity;
    import com.Calculator.calculadora.Request.OperacionRequest;
import com.Calculator.calculadora.Response.OperationResponse;

    @Service
    public interface OperacionService {

        OperacionEntity guardarOperacion(OperacionRequest req, UserEntity usuario);
        List<OperationResponse> obtenerTodas();
    }
