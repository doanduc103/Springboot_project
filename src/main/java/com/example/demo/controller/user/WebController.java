package com.example.demo.controller.user;

import com.example.demo.entity.User;
import com.example.demo.entity.product;
import com.example.demo.exception.ErrorResponse;
import com.example.demo.model.request.CustomUserDetails;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.*;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @GetMapping({"/", "/index"})
    public String index(Model model, product product) {
        List<product> productList = productService.FindByName(product.getName());
        List<product> productList1 = productService.FindByTop1Name(product.getName());
        List<product> products = productRepository.findAll();
        model.addAttribute("productList", productList);
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        model.addAttribute("productList1",productList1);
        return "user/index";
    }



    @GetMapping("/tai-khoan/chi-tiet")
    public String GetUserDetail(@AuthenticationPrincipal UserDetails userLog, Model model) {
        String email = userLog.getUsername();
        User user = userService.FindByEmail(email);
        model.addAttribute("user", user);
        return "user/tai-khoan-chi-tiet";
    }

    @PostMapping(value = "/tai-khoan/update")
//    @ResponseBody
    public String userDetail(@Valid @RequestParam("id") Integer id, @AuthenticationPrincipal CustomUserDetails userDetails, User user, Model model) throws Exception {
        boolean sucess = true;
        if (sucess) {
            userService.UpdateUserLogged(user, id);
            model.addAttribute("message", "Tài khoản của bạn đã cập nhập");
            return "user/tai-khoan-chi-tiet";
        } else {
            model.addAttribute("message", "Cập nhập tài khoản không thành công, vui lòng thử lại !!");
            return "user/tai-khoan-chi-tiet";
        }
    }
}
