package com.example.demo.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DetailProduct {

    @GetMapping("/product/thoi-trang-ao-nam-1")
    public String detail1(){

        return "user/detail-Product";
    }
}
