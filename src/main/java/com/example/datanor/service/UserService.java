package com.example.datanor.service;

import com.example.datanor.exception.InternalException;
import com.example.datanor.exception.ObjectNotFoundException;
import com.example.datanor.model.AppUser;
import com.example.datanor.model.AppUserDto;
import com.example.datanor.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser createUser(AppUserDto user) {
        String username = user.getUsername();
        String password = savePassword(user.getPassword());
        try {
            return userRepository.save(new AppUser(username, password));
        } catch (Exception e) {
            throw new InternalException("Couldn't add user", e);
        }
    }

    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    public String savePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String loginUser(AppUserDto user) {
        if (!validate(user.getUsername(), user.getPassword())){
            throw new ObjectNotFoundException("Wrong password");
        }
        return "Login successful!";
    }

    public boolean validate(String username, String rawPassword){
        String encodedPassword = userRepository.getUserByUsername(username).getPassword();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
