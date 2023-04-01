package com.climate.service;

import com.climate.model.CategoryType;
import com.climate.model.repository.CategoryTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryTypeService {

    @Autowired
    private CategoryTypeRepository categoryTypeRepository;

    public List<CategoryType> findAllByCategoryId(Integer categoryId) {
        return categoryTypeRepository.findAllByCategoryId(categoryId);
    }
}
