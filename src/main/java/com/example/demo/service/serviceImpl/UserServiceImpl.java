package com.example.demo.service.serviceImpl;

import java.util.*;

import com.example.demo.entity.AuthenticationProvider;
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
    public User UpdateUserLogged(User user) throws Exception {
       Optional<User> users = userRepository.findById(user.id);
        if(users.isPresent()){
            user.setEmail(user.email);
            user.setName(user.name);
            user.setPassword(user.password);
            user.setPhone(user.phone);
            return user;
        }
        throw new Exception("User Not found !!");
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
    public User updateUser(User user, Integer id) throws DuplicateMemberException {

        Optional<User> users = userRepository.findById(id);
        if (users.isPresent()) {
            user.setId(id);
            user.setEmail(user.email);
            user.setName(user.name);
            user.setPassword(user.password);
            user.setPhone(user.phone);
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
        user.setPassword(user.password);
        user.setCreated_at(new Date());
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
        user.setAuthenticationProvider(google);
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
//}
