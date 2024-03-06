package com.donggyu.tododemo1.user;

import com.donggyu.tododemo1.ResponseErrorDetail;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> createUser(@RequestBody UserJoinDTO userJoinDTO) {
        try {
            return new ResponseEntity<>(userService.createUser(userJoinDTO), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ResponseErrorDetail(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

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
}
