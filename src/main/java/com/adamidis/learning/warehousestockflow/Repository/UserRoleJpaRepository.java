package com.adamidis.learning.warehousestockflow.Repository;

import com.adamidis.learning.warehousestockflow.Model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleJpaRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUserId(Long userId);
}
