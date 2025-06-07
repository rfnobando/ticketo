package com.group10.ticketo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.group10.ticketo.helpers.ViewRouteHelper;

@Controller
@RequestMapping("/auth")
public class AuthController {
    // This controller will handle authentication-related requests

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(name = "error", required = false) String error,
                        @RequestParam(name = "logout", required = false) String logout) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        return ViewRouteHelper.USER_LOGIN;
    }


    //GET auth/loginSuccess --> Return the view in path home/index if login is successful
    @GetMapping("/loginSuccess")
    public String loginCheck() {
        return ViewRouteHelper.INDEX;
    }
}
