package com.donggyu.tododemo1.user;

import com.donggyu.tododemo1.ResponseErrorDetail;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserJoinDTO userJoinDTO) {
        return userService.createUser(userJoinDTO);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable(value = "id") Long id) {
        return userService.getUser(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long id, @RequestBody UserUpdateDTO userUpdate) {
        try {
            boolean updateResult = userService.updateUser(id, userUpdate);
            if (updateResult) {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("Location", String.format("/api/v1/users/%d", id));
                return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseErrorDetail("Database Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseErrorDetail(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseErrorDetail(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        boolean loginResult = userService.loginUser(userLoginDTO);
        if (loginResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseErrorDetail("Login Fail. Check username & password"), HttpStatus.BAD_REQUEST);
        }
    }
}
