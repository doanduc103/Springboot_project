package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.repository.UserRepository;

@Service
public class JwTUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = repo.findByEmail(name);

        if (user == null) {
            throw new UsernameNotFoundException("email không tồn tại" + user.getEmail());
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }
}
