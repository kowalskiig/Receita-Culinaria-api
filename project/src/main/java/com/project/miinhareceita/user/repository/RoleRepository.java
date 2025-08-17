package com.project.miinhareceita.user.repository;

import com.project.miinhareceita.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByAuthority(String roleName);
}
