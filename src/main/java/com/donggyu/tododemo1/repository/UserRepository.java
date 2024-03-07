package com.donggyu.tododemo1.repository;

import com.donggyu.tododemo1.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    boolean existsByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE users u SET u.email = :email WHERE u.id = :id")
    int updateUserById(Long id, String email);

    @Transactional
    @Query("SELECT u FROM users u WHERE u.username = :username")
    User findByUsername(String username);
}
