package com.group10.ticketo.controllers;

import com.group10.ticketo.dtos.CustomerRegistrationDTO;
import com.group10.ticketo.dtos.EmployeeRegistrationDTO;
import com.group10.ticketo.entities.Department;
import com.group10.ticketo.services.ICustomerService;
import com.group10.ticketo.services.IDepartmentService;
import com.group10.ticketo.services.IEmployeeService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.group10.ticketo.helpers.ViewRouteHelper;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final ICustomerService customerService;
    private final IDepartmentService departmentService;
    private final IEmployeeService employeeService;

    public AuthController(ICustomerService customerService, IDepartmentService departmentService, IEmployeeService employeeService) {
        this.customerService = customerService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/registerEmployee")
    public String showRegistrationEmployeeForm(Model model) {
        List<Department>departments=departmentService.getAllDepartments();
        model.addAttribute("employee", new EmployeeRegistrationDTO());
        model.addAttribute("departments", departments);
        return ViewRouteHelper.REGISTER_EMPLOYEE; // nombre del HTML
    }

    @PostMapping("/registerEmployee")
    public String registerEmployee(@ModelAttribute("employee") @Valid EmployeeRegistrationDTO dto,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", true);
            return ViewRouteHelper.REGISTER_EMPLOYEE;
        }
        employeeService.registerEmployee(dto);

        return "redirect:/";
    }
}