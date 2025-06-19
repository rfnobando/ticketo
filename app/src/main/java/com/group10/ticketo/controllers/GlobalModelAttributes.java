package com.group10.ticketo.controllers;

import com.group10.ticketo.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

@ControllerAdvice
public class GlobalModelAttributes {

    public GlobalModelAttributes() {
    }

    @ModelAttribute("userData")
    public Map<String, Object> populateUserData() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User user) {
            return Map.of(
                    "name", user.getPerson().getName(),
                    "surName", user.getPerson().getSurname());
        } else {
            return Map.of(
                    "name", "",
                    "surName", "");
        }
    }
}