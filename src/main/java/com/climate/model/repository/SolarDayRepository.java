package com.climate.model.repository;

import com.climate.model.SolarDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolarDayRepository extends JpaRepository<SolarDay,Integer> {
}
