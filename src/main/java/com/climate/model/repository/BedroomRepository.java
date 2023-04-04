package com.climate.model.repository;

import com.climate.model.Bedroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedroomRepository extends JpaRepository<Bedroom,Integer> {
}
