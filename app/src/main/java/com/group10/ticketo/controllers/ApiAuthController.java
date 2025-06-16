package com.group10.ticketo.controllers;

import com.group10.ticketo.dtos.ApiLoginRequestDTO;
import com.group10.ticketo.dtos.ApiLoginResponseDTO;
import com.group10.ticketo.services.IApiAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class ApiAuthController {
    private final IApiAuthService apiAuthService;

    public ApiAuthController(IApiAuthService apiAuthService) {
        this.apiAuthService = apiAuthService;
    }

    @PostMapping("/login")
    @Operation(summary = "Autentica un usuario y crea sesión",
            description = "Recibe email y contraseña, autentica y devuelve mensaje de éxito o error.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiLoginResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiLoginResponseDTO.class)))
    })
    public ResponseEntity<ApiLoginResponseDTO> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciales de usuario (email y password)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ApiLoginRequestDTO.class))
            )
            @RequestBody ApiLoginRequestDTO apiLoginRequestDTO,
            HttpServletRequest request) {

            Authentication authentication = apiAuthService.authenticate(
                    apiLoginRequestDTO.email(),
                    apiLoginRequestDTO.password()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            request.getSession(true)
                    .setAttribute(
                            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                            SecurityContextHolder.getContext()
                    );

            return ResponseEntity.ok(new ApiLoginResponseDTO("Login exitoso.", 200));
    }

}
