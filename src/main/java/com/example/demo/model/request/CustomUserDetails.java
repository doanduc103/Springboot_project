package com.example.demo.model.request;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setName(String name) {
        this.user.getName();
    }

    public void setEmail(String email) {
        this.user.getEmail();
    }

    public void setPassword(String password) {
        this.user.getPassword();
    }

    public void setPhone(String phone) {
        this.user.getPhone();
    }

    public boolean setStatus(boolean b) {
        this.user.setStatus(true);
        return true;
    }


    public void setRole(Set<Role> roles) {
        Role role = new Role();
        role.setName("USER");
        roles.add(role);
//        this.user.getRoles();
    }


    public void setCreatedtime(Timestamp timestamp) {
        new Timestamp(System.currentTimeMillis());
    }

}
