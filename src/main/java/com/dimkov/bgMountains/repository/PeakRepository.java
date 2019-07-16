package com.dimkov.bgMountains.repository;

import com.dimkov.bgMountains.domain.entities.Mountain;
import com.dimkov.bgMountains.domain.entities.Peak;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeakRepository extends JpaRepository<Peak,String> {
    Page<Peak> findAll(Pageable pageable);

    Page<Peak> findAllByLocation(Mountain location, Pageable pageable);
}
