package com.example.demo.service.serviceImpl;

import java.util.*;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import javassist.bytecode.DuplicateMemberException;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<UserDTO> getListUSer() {
        List<User> users = userRepository.findAll();
        ArrayList<UserDTO> result = new ArrayList<UserDTO>();
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
    public User updateUser(UserDTO userDTO, Integer id) throws DuplicateMemberException {
//        List<User> users = userRepository.findAll();
//        for (User user : users) {

//            if (user.getId() == id) {
//                if (user.getEmail().equals(userDTO.getEmail())) {
//                    throw new DuplicateMemberException("Email mới đã có người đăng ký");
//                }
//            }
//        }
        Optional<User> users = userRepository.findById(id);
        if (users.isPresent()) {
            User user = users.get();
            user.setEmail(userDTO.getEmail());
            user.setName(userDTO.getName());
            String pass = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(12));
            user.setPassword(pass);
            user.setPhone(userDTO.getPhone());
            userRepository.save(user);
            return user;
        } else {
            throw new NotFoundException("Không tìm thấy id " + id);
        }
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
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

//    @Override
//    public String GetCurrentlyLogged(Authentication authentication) {
//        if (authentication == null) return null;
//        User user = null;
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof UserDetails) {
//            user = ((UserDetails) principal).getUsername();
//        } else {
//            user = principal.toString();
//        }
//
//        return user;
//    }
}
