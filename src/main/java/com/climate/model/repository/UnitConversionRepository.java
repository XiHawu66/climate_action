package com.climate.model.repository;

import com.climate.model.UnitConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitConversionRepository extends JpaRepository<UnitConversion,Integer> {

    @Query(value = "SELECT DISTINCT u FROM UnitConversion u " +
            "WHERE u.fuel = ?1" )
    UnitConversion findByFuel(String Fuel);
}
