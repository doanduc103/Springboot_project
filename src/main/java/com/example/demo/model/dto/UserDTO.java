package com.example.demo.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    @NotBlank(message = "ten ko dc de trong")
    private String name;
    @NotBlank(message = "password ko dc de trong")
    @Size(min = 4, max = 20, message = "password phai tu 4-20")
    private String password;
    @Email
    @NotBlank(message = "Email ko dc de trong")
    private String email;
    @NotBlank(message = "sdt ko dc de trong")
    private String phone;
    private boolean status;
    private String photo;
    private Date created_at;
    private Set<Role> roles;

}
