package com.donggyu.tododemo1.service;

import com.donggyu.tododemo1.user.User;
import com.donggyu.tododemo1.user.UserJoinDTO;
import com.donggyu.tododemo1.repository.UserRepository;
import com.donggyu.tododemo1.user.UserUpdateDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User createUser(UserJoinDTO userJoinDTO) {
        boolean isExist = userRepository.existsByUsername(userJoinDTO.getUsername());

        if (isExist) {
            throw new DataIntegrityViolationException("Username Dulicate");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(userJoinDTO.getPassword());

        User newUser = new User();

        newUser.setUsername(userJoinDTO.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setEmail(userJoinDTO.getEmail());
        newUser.setRole(userJoinDTO.getRole());

        return userRepository.save(newUser);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
    }

    public boolean updateUser(Long id, UserUpdateDTO user) {
        userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        int updateCount = userRepository.updateUserById(id, user.getEmail());
        if (updateCount == 1) {
            return true;
        }
        return false;
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        userRepository.deleteById(id);
    }
}
