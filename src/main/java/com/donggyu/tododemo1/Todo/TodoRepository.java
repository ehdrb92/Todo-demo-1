package com.donggyu.tododemo1.Todo;

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
    @Query("UPDATE TodoUpdate t SET t.content = :content, t.deadline = :deadline WHERE t.id = :id")
    int updateTodoById(Long id, String content, Date deadline);

    @Modifying
    @Transactional
    @Query("UPDATE Todo t SET t.complete = :complete WHERE t.id = :id")
    int completeTodoById(Long id, boolean complete);
}
