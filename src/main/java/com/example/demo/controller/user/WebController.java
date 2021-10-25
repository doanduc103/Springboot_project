package com.example.demo.controller.user;

import com.example.demo.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping(value = "/tai-khoan/chi-tiet")
    public String userDetail(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user",user);
        return "user/tai-khoan-chi-tiet";
    }
}
