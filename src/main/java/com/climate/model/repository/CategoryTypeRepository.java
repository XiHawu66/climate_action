package com.climate.model.repository;

import com.climate.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType,Integer> {

    @Query(value = "SELECT DISTINCT ct FROM CategoryType ct " +
            "WHERE ct.cid = ?1" )
    List<CategoryType> findAllByCategoryId(Integer categoryId);

}
