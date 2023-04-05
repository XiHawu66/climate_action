package com.climate.controller;

import com.climate.model.*;
import com.climate.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/api/footprint_calculation")
    public List<CalculationResult> getCalculation(@RequestBody List<CalculationRequest> calculationRequests) {

        List<CalculationResult> calculationResults = new ArrayList<>();

        CalculationService calculationService = new CalculationService();

        for (CalculationRequest calculationRequest:calculationRequests) {
            Category category = categoryService.findById(Integer.parseInt(calculationRequest.getCid()));
            CategoryType categoryType = categoryTypeService.findById(Integer.valueOf(calculationRequest.getTid()));
            Household household = householdService.findById(Integer.parseInt(calculationRequest.getPid()));
            Bedroom bedroom = bedroomService.findById(Integer.parseInt(calculationRequest.getBid()));
            UnitConversion unitConversion = unitConversionService.findById(categoryType.getFuel());

            calculationResults.add(calculationService.calculate(category,household,bedroom,categoryType,unitConversion));

        }

        return calculationResults;

    }

}
