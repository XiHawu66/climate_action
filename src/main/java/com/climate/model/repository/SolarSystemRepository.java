package com.climate.model.repository;

import com.climate.model.SolarSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolarSystemRepository extends JpaRepository<SolarSystem,Integer> {
}
