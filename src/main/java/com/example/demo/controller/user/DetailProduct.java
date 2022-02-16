package com.example.demo.controller.user;

import com.example.demo.entity.product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Controller
public class DetailProduct {

    @Autowired
    ProductRepository productRepository;


    @GetMapping("/product/{id}")
    public String detail1(@PathVariable("id") Integer id, Model model, product product) {
//        List<product> products = productRepository.findAll();
        Optional<product> product1 = productRepository.findById(id);
        com.example.demo.entity.product productid = product1.get();
        model.addAttribute("product", product);
        model.addAttribute("products", productid);
        model.addAttribute("productid",productRepository.findById(id));
        return "user/detail-Product";
    }

    @GetMapping("/all-product")
    public String allProduct(Model model, product product) {
        Page<product> products = productRepository.findAll(PageRequest.of(0,12));
        model.addAttribute("product", product);
        model.addAttribute("products", products);
//        model.addAttribute("currentPage",page);
//        model.addAttribute("TotalPage",products.getTotalPages());
//        model.addAttribute("TotalItems",products.getTotalElements());
        return "user/ao-product";
    }
}
