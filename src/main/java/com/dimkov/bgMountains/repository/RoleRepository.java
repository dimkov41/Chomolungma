package com.dimkov.bgMountains.repository;

import com.dimkov.bgMountains.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
    Role findByAuthority(String authority);
}
