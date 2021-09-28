package com.example.demo;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication(scanBasePackages = "com.example.demo")

public class SpringbootProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootProjectApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private void CreateUser(){
        User user = new User();
        user.setEmail("doanduc10031993@gmail.com");
        user.setPassword("123123123");
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("ADMIN");
        roles.add(role);
        user.setRoles(roles);
    }
}
