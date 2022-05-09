package com.codegym.service.Account.user;


import com.codegym.model.User;
import com.codegym.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private IUserRepository repository;

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUserName(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return repository.existsByUserName(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public User save(User user) {


        return repository.save(user);
    }


    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Iterable<User> findUsersByNameContaining(String user_name) {
        return repository.findUsersByFullNameContaining(user_name);
    }

    @Override
    public Optional<User> findByFullName(String fullName) {
        return repository.findByFullName(fullName);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }


}
