package com.climate.controller;

import com.climate.model.*;
import com.climate.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class APIController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryTypeService categoryTypeService;

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private BedroomService bedroomService;

    @Autowired
    private UnitConversionService unitConversionService;

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

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/bedroom")
    public List<Bedroom> getBedroom(String bid) {
        return bedroomService.findAll();
    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/footprint_calculation")
    public List<CalculationResult> getCalculation(String[] cidArray, String pid, String bid, String[] tidArray) {

        List<Category> categories = new ArrayList<>();
        List<CategoryType> categoryTypes = new ArrayList<>();
        List<UnitConversion> unitConversions = new ArrayList<>();

        for (String cid:cidArray
             ) {
            Category category = categoryService.findById(Integer.parseInt(cid));
            categories.add(category);
        }

        for (String tid: tidArray
             ) {
            CategoryType categoryType = categoryTypeService.findById(Integer.valueOf(tid));
            categoryTypes.add(categoryType);
            unitConversions.add(unitConversionService.findById(categoryType.getFuel()));
        }


        Household household = householdService.findById(Integer.parseInt(pid));
        Bedroom bedroom = bedroomService.findById(Integer.parseInt(bid));



        CalculationService calculationService = new CalculationService();


        return calculationService.calculate(categories,household,bedroom,categoryTypes,unitConversions);


    }
}
