package com.example.demo.security.oauth;

import com.example.demo.entity.AuthenticationProvider;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();
        String email = oauth2User.getEmail();
        String name = oauth2User.getName();
        System.out.println("Customer's name :" + name);
        System.out.println("Customer's email: " + email);
        User userEmail = userRepository.findByEmail(email);

        if (userEmail == null) {
            // Create a new User email
            userService.createUserAfterLoginOauth2(email,name, AuthenticationProvider.GOOGLE);
        } else {
            //update name with email's exist
            userService.updateUserAfterLoginOauth2(email, name, AuthenticationProvider.GOOGLE);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
