package com.climate.controller;

import com.climate.dto.CalculationDto;
import com.climate.dto.CountryTrendDto;
import com.climate.dto.SolarRecommendationDto;
import com.climate.model.*;
import com.climate.param.CalculationParam;
import com.climate.service.*;
import com.climate.util.CSVUtil;
import com.climate.util.CountryTrendUtil;
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

    @Autowired
    private CalculationService calculationService;

    @CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
    @GetMapping("/api/category_type")
    public List<CategoryType> getCategoryTypes(String cid) {

        return  categoryTypeService.findAllByCategoryId(Integer.parseInt(cid));

    }

    @CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
    @GetMapping("/api/households")
    public List<Household> getHouseholds() {

        return  householdService.findAll();

    }

    @CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
    @GetMapping("/api/household")
    public Household getHousehold(String pid) {

        return  householdService.findById(Integer.parseInt(pid));

    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/bedroom")
    public List<Bedroom> getBedroom(String bid) {
        return bedroomService.findAll();
    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @PostMapping("/api/footprint_calculation")
    public List<CalculationDto> getCalculation(@RequestBody List<CalculationParam> calculationParams) {

        List<CalculationDto> calculationDtos = new ArrayList<>();

        CalculationService calculationService = new CalculationService();

        for (CalculationParam calculationParam : calculationParams) {
            Category category = categoryService.findById(Integer.parseInt(calculationParam.getCid()));
            CategoryType categoryType = categoryTypeService.findById(Integer.valueOf(calculationParam.getTid()));
            Household household = householdService.findById(Integer.parseInt(calculationParam.getPid()));
            Bedroom bedroom = bedroomService.findById(Integer.parseInt(calculationParam.getBid()));
            UnitConversion unitConversion = unitConversionService.findById(categoryType.getFuel());

            calculationDtos.add(calculationService.calculate(category,household,bedroom,categoryType,unitConversion));

        }

        return calculationDtos;

    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/country_trend")
    public CountryTrendDto getCountryTrend(String countryName) {
        String path = "static/API_EN.ATM.CO2E.PC_DS2_en_csv_v2_5358914.csv";

        List<String[]> lines = CSVUtil.readCSV(path);
        return CountryTrendUtil.getCountryTrend(countryName,lines);
    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/produce_solar")
    public List<SolarRecommendationDto> getSolarRecommendation(String totalKwUsage) {

        return calculationService.produceSolar(Double.valueOf(totalKwUsage));
    }

}
