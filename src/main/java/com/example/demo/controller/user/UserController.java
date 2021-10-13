package com.example.demo.controller.user;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.security.JwTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Controller
public class UserController {

    @Autowired
    private JwTTokenUtil jwTTokenUtil;
    public AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String register(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        model.addAttribute("userDTO", userDTO);
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserDTO userDTO, BindingResult br, Model m) {

        User user = userRepository.findByEmail(userDTO.getEmail());
        if (br.hasErrors()) {
            return "user/register";
        }
        userService.createUser(userDTO);
        return "user/register_sucess";
    }

    @GetMapping("/GetUser")
    public ResponseEntity<?> GetListUser() {
        List<UserDTO> user = userService.getListUSer();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/login")
    public String login(Model model, User user) {
        model.addAttribute("user", user);
        return "user/login";
    }

    @GetMapping("/403")
    public String Error() {
        return "user/403";
    }
}

