package com.example.demo.security;

import com.example.demo.model.request.CustomUserDetails;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class JwTUserDetailService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwTUserDetailService.class);
    @Autowired
    private UserRepository repo;

    UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        LOGGER.debug("Authentication user with email" + name);
        User user = repo.findByEmail(name);

        if (user != null) {
            return new CustomUserDetails(user);
        }
//        LOGGER.error("Username not found !");
//        throw new UsernameNotFoundException("email không tồn tại" + user.getEmail());
//        LOGGER.warn("We are testing the spring boot");
//        LOGGER.info("Authentication success !");
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }

//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        UsernamePasswordAuthenticationToken authenticationToken = null;
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        User user = repo.findByEmail(username);
//        if(user != null){
//            if(username.equals(user.getUsername()) && BCrypt.checkpw(password, user.getPassword())){
//
//            }
//        }
//        return null;
//    }
//
//    private Collection<GrantedAuthority> getGrantedAuthorities(User user){
//        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        if(user.getRoles().equals("admin")){
//            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }
//        grantedAuthorities.add()
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return false;
//    }
}
