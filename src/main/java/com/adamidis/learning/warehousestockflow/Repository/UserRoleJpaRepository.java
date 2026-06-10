package com.adamidis.learning.warehousestockflow.Repository;

import com.adamidis.learning.warehousestockflow.Model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRoleJpaRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUserId(Long userId);

    @Modifying
    @Query(value = """
        UPDATE UserRoles SET role_id = :roleId WHERE user_id = :userId""", nativeQuery = true)
    // I'm using native query because my schema naming is inconsistent and i want exact table control
    int updateUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Modifying
    @Query(value = """
        INSERT INTO UserRoles (user_id, role_id) VALUES (:userId, :roleId)""", nativeQuery = true)
    int addRoleToUser(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
