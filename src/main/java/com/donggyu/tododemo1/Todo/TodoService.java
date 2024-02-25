package com.donggyu.tododemo1.Todo;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TodoService {
    private TodoRepository todoRepository;

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo getTodo(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo Not Found"));
    }

    public Iterable<Todo> getTodos() {
        return todoRepository.findAll();
    }

    public Todo updateTodo(Long id, Todo todo) {
        todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo Not Found"));
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo Not Found"));
        todoRepository.deleteById(id);
    }
}
