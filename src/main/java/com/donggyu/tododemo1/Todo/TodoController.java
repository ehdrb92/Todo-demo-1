package com.donggyu.tododemo1.Todo;

import com.donggyu.tododemo1.ResponseErrorDetail;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable(value = "id") Long id, @RequestBody Todo todo) {
        try {
            return new ResponseEntity<>(todoService.updateTodo(id, todo), HttpStatus.OK);
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
