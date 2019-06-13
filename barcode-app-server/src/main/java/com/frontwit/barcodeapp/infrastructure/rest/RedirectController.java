package com.frontwit.barcodeapp.infrastructure.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {

    @RequestMapping("app/**")
    public String index() {
        return "forward:/index.html";
    }
}