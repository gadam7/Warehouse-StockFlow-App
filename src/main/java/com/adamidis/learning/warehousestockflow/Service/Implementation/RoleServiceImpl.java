package com.adamidis.learning.warehousestockflow.Service.Implementation;

import com.adamidis.learning.warehousestockflow.Model.Role;
import com.adamidis.learning.warehousestockflow.Model.UserRole;
import com.adamidis.learning.warehousestockflow.Repository.RoleJpaRepository;
import com.adamidis.learning.warehousestockflow.Repository.RoleRepository;
import com.adamidis.learning.warehousestockflow.Repository.UserRoleJpaRepository;
import com.adamidis.learning.warehousestockflow.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository<Role> roleRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final UserRoleJpaRepository userRoleJpaRepository;

    @Override
    public Role getRoleByUserId(Long id) {

        return roleJpaRepository.findRoleByUserId(id)
                .orElseThrow(() -> new IllegalArgumentException("No role found by name: " + getRoleByUserId(id).getName()));
    }

    @Override
    public Collection<Role> getRoles() {
        return roleJpaRepository.findAll();
    }
}
