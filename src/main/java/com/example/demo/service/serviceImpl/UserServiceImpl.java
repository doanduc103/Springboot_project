package com.example.demo.service.serviceImpl;

import com.example.demo.entity.AuthenticationProvider;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.request.CustomUserDetails;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.oauth.CustomOauth2User;
import com.example.demo.service.UserService;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getListUSer() {
        List<User> users = userRepository.findAll();
        ArrayList<UserDTO> result = new ArrayList<>();
        for (User user : users) {
            result.add(UserMapper.toDto(user));
        }
        return result;
    }

    @Override
    public User createUser(UserDTO userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());

        if (user != null) {
            throw new NotFoundException("tai khoan da ton tai");
        }
        user = UserMapper.toUser(userDto);
        userRepository.save(user);
        return user;
    }

    @Override
    public User UpdateUserLogged(User userEdit, Integer id) throws Exception {
        Optional<User> userid = userRepository.findById(id);
        User Userlogged = userid.get();
        if (userid.isPresent()) {
            Userlogged.setEmail(userEdit.getEmail());
            Userlogged.setName(userEdit.getName());
            Userlogged.setPassword(passwordEncoder.encode(userEdit.getPassword()));
            Userlogged.setPhone(userEdit.getPhone());
            Userlogged.setStatus(true);
            Userlogged.setCreated_at(new Timestamp(System.currentTimeMillis()));
            Role role = new Role();
            role.setName("USER");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            Userlogged.setRoles(roles);
            userRepository.save(Userlogged);
        }
        return Userlogged;

    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);

    }

    @Override
    public boolean login(String email, String password) {
        Optional<User> CheckUser = Optional.of(userRepository.FindUserByEmailAndPassword(email, password));
        if (CheckUser.isPresent() && CheckUser.get().getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public Page<User> GetListUser(Pageable pageable) {
        Page<User> user = userRepository.findAll(PageRequest.of(1, 5));
        return user;
    }

    @Override
    public User updateUser(User editUser, Integer id) throws DuplicateMemberException {
        Optional<User> userid = userRepository.findById(id);
        User user = userid.get();
        if (userid.isPresent()) {
            user.setEmail(editUser.getEmail());
            user.setName(editUser.getName());
            user.setPassword(editUser.getPassword());
            user.setPhone(editUser.getPhone());
            user.setStatus(true);
            user.setCreated_at(new Timestamp(System.currentTimeMillis()));
            Role role = new Role();
            role.setName("USER");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public User deleteUser(Integer id) {
        Optional<User> users = userRepository.findById(id);
        if (users == null) {
            throw new NotFoundException("ID User không tồn tại");
        }
        User user = users.get();
        userRepository.delete(user);
        return user;
    }

    @Override
    public User FindByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void createUserAfterLoginOauth2(String email, String name, AuthenticationProvider provider) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setAuthenticationProvider(provider);
        user.setPassword(user.getPassword());
        user.setCreated_at(new Timestamp(System.currentTimeMillis()));
        user.setStatus(true);
        Role role = new Role();
        role.setName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }


    @Override
    public void updateUserAfterLoginOauth2(String email, String name, AuthenticationProvider google) {
        User user = userRepository.findByEmail(email);
        user.setName(name);
        user.setEmail(email);
        user.setAuthenticationProvider(google);
        user.setStatus(true);
        Role role = new Role();
        role.setName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public List<User> search(String keyword) {
        if (keyword != null) {
            return userRepository.Search(keyword);
        }
        return userRepository.findAll();
    }

    //    @Override
//    public void updateUserLogin(User user) {
//        Optional<User> existUser = userRepository.findById(user.getId());
//        if (existUser.get().getAuthenticationProvider().equals(AuthenticationProvider.LOCAL))
//            if (user.getPassword().isEmpty()) {
//                user.setPassword(existUser.get().getPassword());
//            } else {
//                passwordEncoder(user);
//            }
//        user.setAuthenticationProvider(existUser.get().getAuthenticationProvider());
//        user.setStatus(true);
//        userRepository.save(user);
//    }
//
    @Override
    public User GetCurrentlyLogged(@AuthenticationPrincipal Authentication authentication) {

        User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            user = ((CustomUserDetails) principal).getUser();
        } else if (principal instanceof CustomOauth2User) {
            String email = ((CustomOauth2User) principal).getEmail();
            user = FindByEmail(email);
        }
        return user;
    }

    private void passwordEncoder(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEndcoder = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEndcoder);
    }

    @Override
    public User UpdateAfterLogin(@AuthenticationPrincipal CustomUserDetails userDetails, User user) {
        userDetails.setName(user.getName());
        userDetails.setEmail(user.getEmail());
        userDetails.setPassword(user.getPassword());
        userDetails.setPhone(user.getPhone());
        userDetails.setStatus(true);
        userDetails.setCreatedtime(new Timestamp(System.currentTimeMillis()));
        userDetails.setRole(user.getRoles());
        return userRepository.save(user);
    }

    @Override
            public User getCurrentUser() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication instanceof UsernamePasswordAuthenticationToken) {
                    if (authentication.isAuthenticated()) {
                        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                        return (User) authenticationToken.getPrincipal();
                    }
        }
        throw new BadCredentialsException(MessageCodes.SESSION_EXPIRED);
    }
}

