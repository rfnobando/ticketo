package com.group10.ticketo.controllers;

import com.group10.ticketo.dtos.CustomerRegistrationDTO;
import com.group10.ticketo.services.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.group10.ticketo.helpers.ViewRouteHelper;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final ICustomerService customerService;

    public AuthController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(name = "error", required = false) String error,
                        @RequestParam(name = "logout", required = false) String logout) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        return ViewRouteHelper.USER_LOGIN;
    }

    //Se pueden extraer los permisos de la configuracion de seguridad
    // y redirigir donde sea mas conveniente segun el rol o permisos del usuario.
    @GetMapping("/loginSuccess")
    public String loginCheck() {
        return "redirect:/";
    }

    @GetMapping("/api-login")
    public String login(Model model) {
        return ViewRouteHelper.USER_API_LOGIN;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new CustomerRegistrationDTO());
        return ViewRouteHelper.REGISTER; // nombre del HTML
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute("customer") @Valid CustomerRegistrationDTO dto,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", true);
            return ViewRouteHelper.REGISTER;
        }

        // Si hay error (por ejemplo, email duplicado), se lanza la excepción y se captura globalmente
        customerService.registerCustomer(dto);

        return "redirect:/auth/login?registered=true";
    }

}