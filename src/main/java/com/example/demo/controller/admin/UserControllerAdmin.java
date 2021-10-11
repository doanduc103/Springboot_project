package com.example.demo.controller.admin;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import javax.validation.Valid;

@Controller
public class UserControllerAdmin {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trang-chu/user-custom/{page}")
    public String User(@PathVariable("page") Integer page,Model model) {
        Page<User> user = userRepository.findAll(PageRequest.of(page,8));
        model.addAttribute("currentPage",page);
        model.addAttribute("TotalPage",user.getTotalPages());
        model.addAttribute("totalItem",user.getTotalElements());
        model.addAttribute("user", user);
        return "admin/user-custom";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trang-chu/user-edit")
    public String User(Model model, User user) {
//        userRepository.findById(user.id);
//        model.addAttribute("user", user);
        return "admin/user-edit";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trang-chu/add-user")
    public String AddUser(Model model, UserDTO userDTO) {
        model.addAttribute("userDto", new UserDTO());
        return "admin/add-user";
    }


}
