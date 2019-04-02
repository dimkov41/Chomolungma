package com.dimkov.bgMountains.repository;

import com.dimkov.bgMountains.domain.entities.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MountainRepository extends JpaRepository<Mountain, String> {
    Optional<Mountain> findByName(String name);

    Optional<Mountain> findById(String id);
}
