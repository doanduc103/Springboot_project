package com.example.demo.controller.user;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.User;
import com.example.demo.entity.product;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.ShoppingCartService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ShoppingCartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/tai-khoan/shopping-carts")
    public String ShoppingCarts(@AuthenticationPrincipal Authentication authentication, Model model, product product) {
        User user = userService.GetCurrentlyLogged(authentication);
        List<CartItem> cartItemList = cartRepository.findByUser(user);
        model.addAttribute("cartItems", cartItemList);
        model.addAttribute("PageTittle", "Shopping Cart");
        model.addAttribute("product", product);
        return "user/ShoppingCarts";
    }

    @GetMapping("/product/delete/{pid}")
    public String deleteProduct(@PathVariable("pid") Integer productId,
                                @AuthenticationPrincipal Authentication authentication, Model model,product product) {
        model.addAttribute("product", product);
        User user = userService.GetCurrentlyLogged(authentication);
        if (user == null) {
            return "Bạn phải đăng nhập để thêm vào giỏ hàng";
        }

        shoppingCartService.removeProduct(productId, user);
        return "redirect:/tai-khoan/shopping-carts";
    }

    @GetMapping("/quantity_control")
    public String Cart() {
        return "user/quantity_control";
    }
}
