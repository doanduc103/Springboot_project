package com.example.demo.controller.user;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.security.JwTUserDetailService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ShoppingCartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

//    @GetMapping("/tai-khoan/shopping-carts")
//    public String ShoppingCarts(@AuthenticationPrincipal Authentication authentication, Model model) {
//        User user = userService.GetCurrentlyLogged(authentication);
//        List<CartItem> cartItemList = cartRepository.findByUser(user);
//        model.addAttribute("cartItems", cartItemList);
//        model.addAttribute("PageTittle","Shopping Cart");
//        return "user/ShoppingCarts";
//    }
}
