package com.group10.ticketo.controllers;


import com.group10.ticketo.dtos.CustomerRegistrationDTO;
import com.group10.ticketo.helpers.ViewRouteHelper;
import com.group10.ticketo.services.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class RegistrationController {

    private final ICustomerService customerService;

    public RegistrationController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new CustomerRegistrationDTO());
        return ViewRouteHelper.REGISTER; // nombre del HTML
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute("customer") @Valid CustomerRegistrationDTO dto,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return ViewRouteHelper.REGISTER;
        }

        System.out.println("DTO: " + dto);

        // Si hay error (por ejemplo, email duplicado), se lanza la excepción y se captura globalmente
        customerService.registerCustomer(dto);

        return "redirect:/auth/login?registered=true";
    }
}
