package com.adamidis.learning.warehousestockflow.Repository;

import com.adamidis.learning.warehousestockflow.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    @Query(value = """
    SELECT r.id, r.name, r.permission
    FROM roles r
    JOIN UserRoles ur ON ur.role_id = r.id
    WHERE ur.user_id = :userId
    """, nativeQuery = true)
    Optional<Role> findRoleByUserId(@Param("userId") Long userId);
}
