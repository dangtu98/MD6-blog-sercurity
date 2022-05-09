package com.codegym.service.Account.role;

import com.codegym.model.Role;
import com.codegym.model.RoleName;
import com.codegym.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleRepository repository;

    @Override
    public Optional<Role> findByName(RoleName roleName) {
        return repository.findByName(roleName);
    }
}
