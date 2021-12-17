package com.example.demo.controller.api;

import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.request.AuthenticateReq;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwTTokenUtil;
import com.example.demo.service.UserService;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserControllerAPI {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwTTokenUtil jwTTokenUtil;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    UserService userService;

    private

    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trang-chu/user-view/{page}")
    public ResponseEntity<?> GetListUser(@PathVariable("page") Integer page, Model model) {

        Page<User> result = userService.GetListUser(PageRequest.of(page, 10));

        model.addAttribute("result", result);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("totalItem", result.getTotalElements());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<?> getUserbyID(Model model, @PathVariable(name = "id") Integer id) {
        try {
            System.out.println(id);
            Optional<User> user = userService.findById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        model.addAttribute("user", user);
//        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/create-user")
    public ResponseEntity<?> CreateUser(@Valid @RequestBody UserDTO userDTO, Model model) {
        userService.createUser(userDTO);
//        model.addAttribute("user",user);
        return ResponseEntity.ok("tạo user thành công");
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> EditUser(@RequestBody User user, @PathVariable(name = "id") Integer id, Model model) throws DuplicateMemberException {
        userService.updateUser(user, id);
        return ResponseEntity.ok("Đã cập nhập User " + id);
    }

    @DeleteMapping(value = "/api/user/{id}")
    public ResponseEntity<?> DeleteUser(@PathVariable(name = "id") Integer id) {
        try {
            System.out.println(id);
            userService.deleteUser(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/authentication")
    public @ResponseBody
    ResponseEntity<?> login(@Valid @RequestBody AuthenticateReq authenticateReq) throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticateReq.getEmail(), authenticateReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwTTokenUtil.generationToken((UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(token);
    }
}
