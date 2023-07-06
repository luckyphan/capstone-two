package com.devmountain.myBestFriend.services;

import com.devmountain.myBestFriend.dtos.UserDto;
import com.devmountain.myBestFriend.entities.User;
import com.devmountain.myBestFriend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    //register user
    //verify user
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Register User
    @Override
    @Transactional
    public List<String> addUser(UserDto userDto){
        List<String> response = new ArrayList<>();
        User user = new User(userDto);
        userRepository.saveAndFlush(user);
        response.add("Account is registered");
        return response;
    }

    //Login User
    @Override
    public List<String> userLogin(UserDto userDto){
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if(userOptional.isPresent()){
            if(passwordEncoder.matches(userDto.getPassword(),userOptional.get().getPassword())){
                response.add("Successfully logged in");

            }else{
                response.add("Invalid login");
            }
        }
        else{
            response.add("Invalid login");
        }
        return response;
    }

}
