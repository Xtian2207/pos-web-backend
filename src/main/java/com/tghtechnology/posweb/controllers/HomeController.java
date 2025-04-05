package com.tghtechnology.posweb.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping("home")
    public String home() {
        return "Bienvenido a la página principal!";
    }
}