package com.Calculator.calculadora.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "historial")
public class OperacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expresion;
    private String resultado;

    @Column(name = "tipo_operacion")
    private String tipoOperacion;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "user")
    private String username;  // mapea la columna string 'user' de la tabla

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;  // FK, relación con UserEntity
}
