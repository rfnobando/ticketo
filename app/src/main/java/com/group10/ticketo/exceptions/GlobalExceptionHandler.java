package com.group10.ticketo.exceptions;

import com.group10.ticketo.dtos.CustomerRegistrationDTO;
import com.group10.ticketo.helpers.ViewRouteHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Aca se  pueden manejar excepciones globales
    // Por ejemplo, capturar excepciones específicas y devolver un mensaje personalizado

    @ExceptionHandler(UserValidationException .class)
    public String handleEmailAlreadyRegistered(UserValidationException  ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("customer", new CustomerRegistrationDTO());
        return ViewRouteHelper.REGISTER;
    }
}
