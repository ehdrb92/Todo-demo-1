package com.donggyu.tododemo1.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
    }

    public boolean updateUser(Long id, UserUpdate user) {
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
