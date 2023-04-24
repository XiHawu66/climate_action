package com.climate.model.repository;

import com.climate.model.SolarConst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolarConstRepository extends JpaRepository<SolarConst,Integer> {
}
