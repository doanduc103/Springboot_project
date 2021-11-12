package com.example.demo.model.mapper;

import java.sql.Timestamp;
import java.util.*;

import com.example.demo.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.example.demo.entity.User;
import com.example.demo.model.dto.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    //	@Autowired
//	private ModelMapper modelMapper;
    @Autowired
    private static PasswordEncoder passwordEncoder;

    // convert từ DTO >> Entity
    public static User toUser(UserDTO userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
//        String pass = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(12));
        user.setPassword(userDto.getPassword());
        user.setCreated_at(new Timestamp(System.currentTimeMillis()));
        user.setStatus(true);
        Role role = new Role();
        boolean choosen = false;
        if(choosen){
            role.setName("ADMIN");
        } else{
            role.setName("USER");
        }
//        role.setName("USER");
//        role.setName("ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        user.setPhoto(userDto.getPhoto());
        return user;

    }

    // convert từ entity >> DTO
    public static UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setPhoto(user.getPhoto());
//        dto.setRoles((List<Role>) user.getRoles());
        return dto;
    }
}
