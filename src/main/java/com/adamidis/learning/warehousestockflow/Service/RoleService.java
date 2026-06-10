package com.adamidis.learning.warehousestockflow.Service;

import com.adamidis.learning.warehousestockflow.Model.Role;

import java.util.Collection;

public interface RoleService {
    Role getRoleByUserId(Long id);

    Collection<Role> getRoles();

    int updateUserRole(Long userId, String roleName);
}
