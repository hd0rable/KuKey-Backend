package com.example.Kukey_Backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Appcontroller {

    @GetMapping("/")
    public String home(){
        return "Hello World";
    }
}
