package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.AuthenticationProvider;
import com.example.demo.entity.Role;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.model.dto.UserDTO;

@Service
public interface UserService {

    public User createUser(UserDTO userDTO);

    public User UpdateUserLogged(User user) throws Exception;

    public List<UserDTO> getListUSer();

    public Optional<User> findById(Integer id);

    public boolean login(String email, String password);

    public Page<User> GetListUser(Pageable pageable);

    public User updateUser(User user, Integer id) throws DuplicateMemberException;

    User deleteUser(Integer id);

    User FindByEmail(String email);

    List<Role> findAll();

    void createUserAfterLoginOauth2(String email, String name, AuthenticationProvider provider);

    void updateUserAfterLoginOauth2(String email, String name, AuthenticationProvider google);

    List<User> search(String keyword);
//	String GetCurrentlyLogged(Authentication authentication);
}
