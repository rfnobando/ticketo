package com.group10.ticketo.controllers;

import com.group10.ticketo.dtos.ApiLoginRequestDTO;
import com.group10.ticketo.dtos.ApiLoginResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class ApiAuthController {
    private final AuthenticationManager authenticationManager;

    public ApiAuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiLoginResponseDTO> login(@RequestBody ApiLoginRequestDTO apiLoginRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(apiLoginRequestDTO.email(), apiLoginRequestDTO.password())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok(new ApiLoginResponseDTO("Login success", 200));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiLoginResponseDTO("Invalid credentials", 401)
            );
        }
    }

}
