package com.dimkov.bgMountains.repository;

import com.dimkov.bgMountains.domain.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Authority,String> {
    Authority findByAuthority(String authority);
}
