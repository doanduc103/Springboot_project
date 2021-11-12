package com.example.demo.controller.user;

import com.example.demo.entity.User;
import com.example.demo.model.request.CustomUserDetails;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/tai-khoan/chi-tiet")
    public String GetUserDetail(@AuthenticationPrincipal UserDetails userLog, Model model) {
        String email = userLog.getUsername();
        User user = userService.FindByEmail(email);
        model.addAttribute("user", user);
        return "user/tai-khoan-chi-tiet";
    }

//    @GetMapping("/tai-khoan/update/{id}")
//    public Optional<User> Getdetails(User user){
//        return userRepository.findById(user.id);
//    }

    @PostMapping(value = "/tai-khoan/update")
//    @ResponseBody
    public String userDetail(@RequestParam("id") Integer id, @AuthenticationPrincipal CustomUserDetails userDetails, User user, Model model) throws Exception {
//        userService.UpdateUserLogged(user);
        userDetails.setName(user.getName());
        userDetails.setEmail(user.getEmail());
        userDetails.setPassword(user.getPassword());
        userDetails.setPhone(user.getPhone());
        userService.updateUser(user,id);
        model.addAttribute("message", "Tài khoản của bạn đã cập nhập");
        model.addAttribute("message", "Cập nhập không thành công");

//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.name,user.password);
//
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return "user/tai-khoan-chi-tiet";
    }
}
