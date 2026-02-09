package com.ered.tasker.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ered.tasker.domain.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
}
