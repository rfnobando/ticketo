package com.group10.ticketo.services.implementation;

import com.group10.ticketo.exceptions.ApiInvalidCredentialsException;
import com.group10.ticketo.services.IApiAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class ApiAuthService implements IApiAuthService {
    private final AuthenticationManager authenticationManager;

    public ApiAuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication authenticate(String email, String password) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (AuthenticationException ex) {
            throw new ApiInvalidCredentialsException("Credenciales inválidas.");
        }
    }
}
