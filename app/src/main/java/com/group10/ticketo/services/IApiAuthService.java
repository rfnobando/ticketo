package com.group10.ticketo.services;

import org.springframework.security.core.Authentication;

public interface IApiAuthService {
    Authentication authenticate(String email, String password);
}
