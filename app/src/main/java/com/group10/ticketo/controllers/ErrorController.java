package com.group10.ticketo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
    @GetMapping("/403")
    public String accessDenied(Model model) {
        model.addAttribute("error", "No tenés permiso para acceder a este recurso.");
        return "errors/403"; // Carga la vista en templates/errors/403.html
    }
}
