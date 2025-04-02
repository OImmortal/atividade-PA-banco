package org.example.repository;

import org.example.entities.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryEntity {
    void save(UserEntity userEntity);
    Optional<UserEntity> findById(UUID id);
    List<UserEntity> findAll();
    void deleteById(UUID id);
}
