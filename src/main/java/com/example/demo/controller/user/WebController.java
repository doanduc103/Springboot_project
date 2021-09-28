package com.example.demo.controller.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

//	private static final int MAX_AGE_COOKIE = 0;

//	@Autowired
//	public JwTTokenUtil jwTTokenUtil;

    @GetMapping({"/", "/index"})
    public String index() {
        return "user/index";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/tai-khoan/chi-tiet")
    public String userDetail() {
        return "user/tai-khoan-chi-tiet";
    }
}
