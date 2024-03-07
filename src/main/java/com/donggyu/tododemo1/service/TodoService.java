package com.donggyu.tododemo1.service;

import com.donggyu.tododemo1.todo.Todo;
import com.donggyu.tododemo1.repository.TodoRepository;
import com.donggyu.tododemo1.todo.TodoUpdateDTO;
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

    public boolean updateTodo(Long id, TodoUpdateDTO todoUpdateDTO) {
        todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo Not Found"));
        int updateCount = todoRepository.updateTodoById(id, todoUpdateDTO.getContent(), todoUpdateDTO.getDeadline());
        if (updateCount == 1) {
            return true;
        }
        return false;
    }

    public boolean completeTodo(Long id) {
        todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo Not Found"));
        int updateCount = todoRepository.completeTodoById(id, true);
        if (updateCount == 1) {
            return true;
        }
        return false;
    }

    public void deleteTodo(Long id) {
        todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo Not Found"));
        todoRepository.deleteById(id);
    }
}
