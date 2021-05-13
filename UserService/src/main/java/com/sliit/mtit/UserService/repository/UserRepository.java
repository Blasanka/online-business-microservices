package com.sliit.mtit.UserService.repository;

import com.sliit.mtit.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user WHERE email= :email AND password= :password LIMIT 1",
            nativeQuery = true)
    Optional<User> findUserByCredentials(String email, String password);
}
