package com.donggyu.tododemo1.repository;

import com.donggyu.tododemo1.todo.Todo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE todos t SET t.content = :content, t.deadline = :deadline WHERE t.id = :id")
    int updateTodoById(Long id, String content, Date deadline);

    @Modifying
    @Transactional
    @Query("UPDATE todos t SET t.isComplete = :isComplete WHERE t.id = :id")
    int completeTodoById(Long id, Boolean isComplete);
}
