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

    @PostMapping("product/cart/add/{pid}/{qty}")
    public String addProductToCart(@PathVariable("pid") Integer productId,
                                   @PathVariable("qty") Integer quantity,
                                   @AuthenticationPrincipal Authentication authentication) {
        System.out.println("addproduct " + productId +  "-" + quantity);
        User user = userService.GetCurrentlyLogged(authentication);
        System.out.println(user);
        if(user == null){
            return "Bạn phải đăng nhập để thêm vào giỏ hàng";
        }

        Integer addedCartItem = shoppingCartService.addProduct(productId,quantity,user);

        System.out.println("added" + addedCartItem);
        return addedCartItem + "Sản phẩm đã được thêm vào giỏ hàng của bạn";
    }

    @PostMapping("tai-khoan/cart/update/{pid}/{qty}")
    public String UpdateQuantity(@PathVariable("pid") Integer productId,
                                   @PathVariable("qty") Integer quantity,
                                   @AuthenticationPrincipal Authentication authentication) {
        System.out.println("addproduct " + productId +  "-" + quantity);
        User user = userService.GetCurrentlyLogged(authentication);
        System.out.println(user);
        if(user == null){
            return "Bạn phải đăng nhập để thêm vào giỏ hàng";
        }

        float subtotal = shoppingCartService.UpdateQuantity(productId,quantity,user);

        return String.valueOf(subtotal);
    }
}
