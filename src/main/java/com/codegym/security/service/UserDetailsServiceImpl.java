package com.codegym.security.service;


import com.codegym.model.User;
import com.codegym.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    IUserRepository iUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = iUserRepository.findByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

        return UserPrinciple.build(user);
    }
}
