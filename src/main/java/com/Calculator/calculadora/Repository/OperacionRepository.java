package com.Calculator.calculadora.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Calculator.calculadora.Entity.OperacionEntity;

public interface OperacionRepository extends JpaRepository<OperacionEntity, Long> {

}
