package com.devit.chat.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping(value = "/main")
    public String init() {
        return "index.html";
    }

    @RequestMapping(value = "/detail")
    public String detail() {
        return "chat.html";
    }
}
