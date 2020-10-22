package com.sainsburys.psr.fcrs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/swagger-ui.html";
    }
}
