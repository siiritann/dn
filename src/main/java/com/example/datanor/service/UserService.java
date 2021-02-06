package com.example.datanor.service;

import com.example.datanor.exception.InternalException;
import com.example.datanor.model.AppUser;
import com.example.datanor.model.AppUserDto;
import com.example.datanor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser createUser(AppUserDto user) {
        try {
            return userRepository.save(new AppUser(user));
        } catch (Exception e) {
            throw new InternalException("Couldn't add user", e);
        }
    }

    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }
}
