package com.oz.importingApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {


    @GetMapping
    public String getMain() {
        return "redirect:/main.html";
    }
}
