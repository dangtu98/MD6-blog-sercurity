package com.codegym.service.Account.role;


import com.codegym.model.Role;
import com.codegym.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName roleName);
}
