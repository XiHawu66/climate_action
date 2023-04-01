package com.climate.controller;

import com.climate.model.CategoryType;
import com.climate.service.CategoryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryTypeController {
    @Autowired
    private CategoryTypeService categoryTypeService;

    @CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
    @GetMapping("/category_type")
    public List<CategoryType> getCategoryTypes(String cid) {

        List<CategoryType> categoryTypes = categoryTypeService.findAllByCategoryId(Integer.parseInt(cid));

        return categoryTypes;

    }
}
