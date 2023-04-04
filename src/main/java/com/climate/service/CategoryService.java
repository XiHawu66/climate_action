package com.climate.service;

import com.climate.model.Category;
import com.climate.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category findById(Integer pid) {
        return categoryRepository.findById(pid).get();
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
