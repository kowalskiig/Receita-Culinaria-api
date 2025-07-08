package com.project.miinhareceita.repositories;

import com.project.miinhareceita.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
