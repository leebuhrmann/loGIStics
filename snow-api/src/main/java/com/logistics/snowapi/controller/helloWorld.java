package com.logistics.snowapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloWorld {
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello world from Backend";
    }
}
