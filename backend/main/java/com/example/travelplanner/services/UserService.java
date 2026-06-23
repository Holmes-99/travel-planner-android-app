package com.example.travelplanner.services;

import com.example.travelplanner.models.User;
import com.example.travelplanner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User addUser (User user){
        return userRepository.save(user);
    }

    public User updateUser(User user , Integer id){
        user.setId(id);
    return userRepository.save(user);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }


}
