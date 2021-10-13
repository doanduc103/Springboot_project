package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Custommer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "email")
    public String email;
    @Column(name = "first_name")
    public String first_name;
    @Column(name = "last_name")
    public String last_name;
    @Column(name = "phone_number")
    public String phone_number;
    @Column(name = "create_time")
    public Date create_time;
    @Column(name = "enable")
    public boolean enable;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    public AuthenticationProvider authentication;
}
