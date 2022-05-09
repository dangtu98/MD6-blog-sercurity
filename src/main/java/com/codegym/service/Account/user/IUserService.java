package com.codegym.service.Account.user;

import com.codegym.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

    Optional<User> findById(Long id);

    User save(User user);

    Iterable<User> findAll();

    void delete(Long id);

    Iterable<User> findUsersByNameContaining(String user_name);

    Optional<User> findByFullName(String fullName);

    Optional<User> findByEmail(String email);

}
