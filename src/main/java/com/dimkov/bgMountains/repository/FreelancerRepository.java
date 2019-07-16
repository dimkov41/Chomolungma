package com.dimkov.bgMountains.repository;

import com.dimkov.bgMountains.domain.entities.Freelancer;
import org.hibernate.hql.internal.ast.tree.FromElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer,String> {
    Page<Freelancer> findAll(Pageable pageable);

    Optional<Freelancer> findByUserUsername(String username);
}
