package com.group10.ticketo.controllers;

import com.group10.ticketo.helpers.ViewRouteHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView(ViewRouteHelper.INDEX);
        mv.addObject("title", "Ticketo");//es un ejemplo
        return mv;
    }
}