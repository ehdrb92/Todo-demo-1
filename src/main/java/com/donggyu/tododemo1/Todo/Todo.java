package com.donggyu.tododemo1.Todo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity(name = "todos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "deadline", nullable = true)
    private Date deadline;

    @Column(name = "is_complete", nullable = false)
    private Boolean isComplete;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;
}
