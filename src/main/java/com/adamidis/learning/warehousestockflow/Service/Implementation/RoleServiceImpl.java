package com.adamidis.learning.warehousestockflow.Service.Implementation;

import com.adamidis.learning.warehousestockflow.Exception.ApiException;
import com.adamidis.learning.warehousestockflow.Model.Role;
import com.adamidis.learning.warehousestockflow.Model.UserRole;
import com.adamidis.learning.warehousestockflow.Repository.RoleJpaRepository;
import com.adamidis.learning.warehousestockflow.Repository.RoleRepository;
import com.adamidis.learning.warehousestockflow.Repository.UserRoleJpaRepository;
import com.adamidis.learning.warehousestockflow.Service.RoleService;
import jakarta.transaction.Transactional;
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

    @Override
    @Transactional
    public int updateUserRole(Long userId, String roleName) {
        Role role = roleJpaRepository.findByName(roleName)
                .orElseThrow(() -> new ApiException("No role found by name: " + roleName));

        int updateRows = userRoleJpaRepository.updateUserRole(userId, role.getId());
        if (updateRows == 0) {
            throw new ApiException("No user role mapping found for user id: " + userId);
        }

        return updateRows;
    }
}
