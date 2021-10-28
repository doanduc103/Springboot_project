package com.example.demo.controller.user;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping({"/", "/index"})
    public String index() {
        return "user/index";
    }

//    @GetMapping("/tai-khoan/chi-tiet")
//    public String GetUserDetail(Model model){
//        return "user/tai-khoan-chi-tiet";
//    }

    @GetMapping(value = "/tai-khoan/chi-tiet/{id}")
    @ResponseBody
    public String userDetail(@Valid @AuthenticationPrincipal User user, Model model, @PathVariable(name = "id") Integer id) {
       Optional<User> UserProfile = userService.findById(id);
        if(UserProfile.isPresent()){
            model.addAttribute("userid", UserProfile);
        } else {
            model.addAttribute("user",user);
        }
        return "user/tai-khoan-chi-tiet";
    }
}
