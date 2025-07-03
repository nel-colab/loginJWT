package com.Calculator.calculadora.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Calculator.calculadora.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUser(String user);
}
