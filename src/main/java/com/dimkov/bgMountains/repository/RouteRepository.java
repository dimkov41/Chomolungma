package com.dimkov.bgMountains.repository;

import com.dimkov.bgMountains.domain.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, String> {
}
