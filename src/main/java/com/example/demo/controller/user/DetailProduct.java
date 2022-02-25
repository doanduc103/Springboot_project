package com.example.demo.controller.user;

import com.example.demo.entity.product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Controller
public class DetailProduct {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;


    @GetMapping("/product/{id}")
    public String detail1(@PathVariable("id") Integer id, Model model) {
        product product = productService.getProductById(id);
        model.addAttribute("product", product);
//        model.addAttribute("productid",productRepository.findById(id));
        return "user/detail-Product";
    }

    @GetMapping("/all-product/{page}")
    public String allProduct(@PathVariable("page") Integer page, Model model, product product) {
        Page<product> products = productRepository.findAll(PageRequest.of(page, 16));
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("TotalPage", products.getTotalPages());
        model.addAttribute("TotalItems", products.getTotalElements());
        return "user/ao-product";
    }

    @GetMapping("all-product/ascending")
    public String allProductSortByAscending(@RequestParam(value = "page",required = true) Integer page,Model model, product product) {
        Page<product> products = productService.findAllProductByPriceAscending(page);
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("TotalPage", products.getTotalPages());
        model.addAttribute("TotalItems", products.getTotalElements());
        return "user/ao-product";
    }

    @GetMapping("all-product/descending")
    public String allProductSortByDescending(@RequestParam Integer page,Model model, product product) {
        Page<product> products = productRepository.findAllProductByPriceDescending(PageRequest.of(page, 16));
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("TotalPage", products.getTotalPages());
        model.addAttribute("TotalItems", products.getTotalElements());
        return "user/ao-product";
    }

    @GetMapping("all-product/PriceDescending")
    public String allProductSortByPriceDescending(@RequestParam(value = "page",required = true)Integer page,
                                                  Model model, product product) {

        Page<product> products = productRepository.FindAllProDuctByCreatedDate(PageRequest.of(0, 16));
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("TotalPage", products.getTotalPages());
        model.addAttribute("TotalItems", products.getTotalElements());
        return "user/ao-product";
    }

    @GetMapping("all-product/search")
    public String SearchProduct(Model model, product product,@RequestParam(required = false)
                                 String keyword,Integer page) {
        Page<product> products = productRepository.SearchProductByAvailableAndCreatedDateAndPriceAndName(PageRequest.of(page,16),keyword);
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("TotalPage", products.getTotalPages());
        model.addAttribute("TotalItems", products.getTotalElements());
        return "user/ao-product";
    }
}
