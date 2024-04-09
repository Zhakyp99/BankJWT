package com.example.solidbank.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.solidbank.security.domain.dto.JwtAuthenticationResponse;
import com.example.solidbank.security.domain.dto.SignInRequest;
import com.example.solidbank.security.domain.dto.SignUpRequest;
import com.example.solidbank.security.service.AuthenticationService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "authentication")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "user registration")
    @PostMapping("/register")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "user authorization")
    @PostMapping("/authenticate")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}

