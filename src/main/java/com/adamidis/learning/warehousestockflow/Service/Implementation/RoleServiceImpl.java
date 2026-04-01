package com.adamidis.learning.warehousestockflow.Service.Implementation;

import com.adamidis.learning.warehousestockflow.Model.Role;
import com.adamidis.learning.warehousestockflow.Repository.RoleRepository;
import com.adamidis.learning.warehousestockflow.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository<Role> roleRepository;

    @Override
    public Role getRoleByUserId(Long id) {
        return roleRepository.getRoleByUserId(id);
    }

    @Override
    public Collection<Role> getRoles() {
        return roleRepository.list();
    }
}
