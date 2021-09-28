package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entity.product;
import com.example.demo.service.ProductService;

@Controller
public class AdminController {

	@Autowired
	ProductService productService;
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/trang-chu")
	public String home() {
		return "admin/admin_home";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("trang-chu/quan-clothes")
	public String paints() {
		return "admin/quan-clothes";
	}
}
