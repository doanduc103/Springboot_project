package com.example.demo.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingCartController {
    @GetMapping("/tai-khoan/shopping-carts")
    public String ShoppingCarts(){

        return "user/ShoppingCarts";
    }
}
