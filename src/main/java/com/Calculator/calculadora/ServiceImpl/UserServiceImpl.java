package com.Calculator.calculadora.ServiceImpl;

import com.Calculator.calculadora.Entity.UserEntity;
import com.Calculator.calculadora.Exception.UserAlreadyExistsException;
import com.Calculator.calculadora.Repository.UserRepository;
import com.Calculator.calculadora.Request.UserRequest;
import com.Calculator.calculadora.Response.UserResponse;
import com.Calculator.calculadora.Service.UserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse registrarUsuario(UserRequest usuario) {
        if (userRepository.findByUser(usuario.getUser()).isPresent()) {
            throw new UserAlreadyExistsException("El nombre de usuario ya está registrado");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUser(usuario.getUser());
        userEntity.setNombre(usuario.getNombre());
        userEntity.setEmail(usuario.getEmail());
        userEntity.setPassword(passwordEncoder.encode(usuario.getPassword()));

        UserEntity saved = userRepository.save(userEntity);
        return new UserResponse(saved.getUser(), saved.getNombre(), saved.getEmail());
    }

    /**
     * Método de UserDetailsService para Spring Security.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUser(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado: " + username)
            );

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUser())
            .password(user.getPassword())
            .roles("USER")
            .build();
    }

    @Override
    public UserEntity findByUser(String user) {
        return userRepository.findByUser(user).orElse(null);
    }
}
