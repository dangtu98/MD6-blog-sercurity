package com.codegym.repository;



import com.codegym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUserName(String username);
    Iterable<User> findUsersByFullNameContaining(String user_name);
    Optional<User> findByFullName(String fullName);
    Optional<User> findByEmail(String email);

}
