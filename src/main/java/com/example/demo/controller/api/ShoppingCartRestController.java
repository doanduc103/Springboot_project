package com.example.demo.controller.api;

import com.example.demo.entity.User;
import com.example.demo.service.ShoppingCartService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingCartRestController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @PostMapping("/cart/add/{pid}/{qty}")
    public String addProductToCart(@PathVariable("pid") Integer productId,
                                   @PathVariable("qty") Integer quantity,
                                   @AuthenticationPrincipal Authentication authentication) {

        System.out.println("addproduct" + productId +  "-" + quantity);
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "Bạn phải đăng nhập !!..";
        }
        User user = userService.GetCurrentlyLogged(authentication);

        Integer addedCartItem = shoppingCartService.addProduct(productId,quantity,user);

        System.out.println("added");
        return addedCartItem + "Sản phẩm đã được thêm vào giỏ hàng của bạn";
    }
}
