package com.donggyu.tododemo1.controller;

import com.donggyu.tododemo1.ResponseErrorDetail;
import com.donggyu.tododemo1.todo.Todo;
import com.donggyu.tododemo1.service.TodoService;
import com.donggyu.tododemo1.todo.TodoUpdateDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/todos")
@AllArgsConstructor
public class TodoController {
    private TodoService todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.createTodo(todo);
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable(value = "id") Long id) {
        return todoService.getTodo(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Todo> getTodos() {
        return todoService.getTodos();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable(value = "id") Long id, @RequestBody TodoUpdateDTO todoUpdateDTO) {
        try {
            boolean updateResult = todoService.updateTodo(id, todoUpdateDTO);
            if (updateResult) {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("Location", String.format("/api/v1/todos/%d", id));
                return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseErrorDetail("Database Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseErrorDetail(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> completeTodo(@PathVariable(value = "id") Long id) {
        try {
            boolean updateResult = todoService.completeTodo(id);
            if (updateResult) {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("Location", String.format("/api/v1/todos/%d", id));
                return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseErrorDetail("Database Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseErrorDetail(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable(value = "id") Long id) {
        try {
            todoService.deleteTodo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseErrorDetail(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
