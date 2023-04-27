package com.climate.model.repository;

import com.climate.model.BedroomCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedroomCategoryRepository extends JpaRepository<BedroomCategory,Integer> {

    BedroomCategory findBedroomCategoryByBidAndCid(Integer bid, Integer cid);
}
