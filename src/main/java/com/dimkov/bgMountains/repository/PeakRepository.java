package com.dimkov.bgMountains.repository;

import com.dimkov.bgMountains.domain.entities.Peak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeakRepository extends JpaRepository<Peak,String> {
}
