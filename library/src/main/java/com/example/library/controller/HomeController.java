package com.example.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "forward:/home/index.html";
    }
    @GetMapping("/rent")
    public String rent() {
        return "rent";
    }
 
}
//ページは作成まだ