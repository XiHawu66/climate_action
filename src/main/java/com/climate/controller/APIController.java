package com.climate.controller;

import com.climate.model.CategoryType;
import com.climate.model.Household;
import com.climate.service.CategoryTypeService;
import com.climate.service.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class APIController {
    @Autowired
    private CategoryTypeService categoryTypeService;

    @Autowired
    private HouseholdService householdService;

    @CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
    @GetMapping("/api/category_type")
    public List<CategoryType> getCategoryTypes(String cid) {

        return  categoryTypeService.findAllByCategoryId(Integer.parseInt(cid));

    }

    @CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
    @GetMapping("/api/household")
    public List<Household> getHouseholds(String pid) {

        return  householdService.findAll();

    }

    /*@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/calculate")
    public List<Household> getCalculationResult() {
        return ;
    }*/
}
