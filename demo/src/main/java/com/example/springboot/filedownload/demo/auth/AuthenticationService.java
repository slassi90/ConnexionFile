package com.example.springboot.filedownload.demo.auth;

import com.example.springboot.filedownload.demo.Config.JwtService;
import com.example.springboot.filedownload.demo.Repository.UserRepository;
import com.example.springboot.filedownload.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                 //.role(Role.USER)

     .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail()
                ,request.getPassword()));
        var user  = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwttoken = jwtService.generateToken(user);
        return AuthenticationResponse .builder()
                .token(jwttoken)
                .build();

    }

}
