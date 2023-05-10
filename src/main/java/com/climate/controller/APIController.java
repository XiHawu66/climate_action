package com.climate.controller;

import com.climate.dto.*;
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

    @Autowired
    private SolarSystemService solarSystemService;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private BedroomCategoryService bedroomCategoryService;

    @CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
    @GetMapping("/api/category_type")
    public List<CategoryType> getCategoryTypes(String cid) {

        return categoryTypeService.findAllByCategoryId(Integer.parseInt(cid));

    }

    @CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
    @GetMapping("/api/households")
    public List<Household> getHouseholds() {

        return householdService.findAll();

    }

    @CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
    @GetMapping("/api/household")
    public Household getHousehold(String pid) {

        return householdService.findById(Integer.parseInt(pid));

    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/bedroom")
    public List<Bedroom> getBedroom() {
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
            BedroomCategory bedroomCategory = bedroomCategoryService.findByBidAndCid(bedroom.getBid(),category.getCid());

            calculationDtos.add(calculationService.calculate(category,household,bedroom,categoryType,unitConversion,bedroomCategory,calculationParam.getLoad()));

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
    public List<SolarRecommendationDto> getSolarRecommendation(String totalKwUsage, String isRebate) {
        boolean isRebateBool = isRebate.equals("true");

        return calculationService.produceSolar(Double.valueOf(totalKwUsage), isRebateBool);
    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/solar_system")
    public List<SolarSystem> getSolarSystems() {

        return solarSystemService.getSolarSystems();
    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/solar_offset")
    public SolarOffsetDto getSolarOffset(String kw) {

        return calculationService.getAnnualGeneration(Integer.parseInt(kw));
    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/hot_water_recommendation")
    public HotWaterRecommendationDto getHotWaterRecommendation(String pid, String cid, String tid) {
        Household household = householdService.findById(Integer.parseInt(pid));
        Category category = categoryService.findById(Integer.parseInt(cid));
        CategoryType categoryType = categoryTypeService.findById(Integer.valueOf(tid));

        UnitConversion unitConversion = unitConversionService.findById(categoryType.getFuel());

        return recommendationService.getHotWaterRecommendationDto(household, category, categoryType, unitConversion);
    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/lighting_recommendation")
    public LightingRecommendationDto getLightingRecommendation(String pid, String cid, String tid) {
        Household household = householdService.findById(Integer.parseInt(pid));
        Category category = categoryService.findById(Integer.parseInt(cid));
        CategoryType categoryType = categoryTypeService.findById(Integer.valueOf(tid));

        UnitConversion unitConversion = unitConversionService.findById(categoryType.getFuel());

        return recommendationService.getLightingRecommendationDto(household, category, categoryType, unitConversion);
    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/fridge_recommendation")
    public FridgeRecommendationDto getFridgeRecommendation(String pid, String cid, String tid) {
        Household household = householdService.findById(Integer.parseInt(pid));
        Category category = categoryService.findById(Integer.parseInt(cid));
        CategoryType categoryType = categoryTypeService.findById(Integer.valueOf(tid));

        UnitConversion unitConversion = unitConversionService.findById(categoryType.getFuel());

        return recommendationService.getFridgeRecommendationDto(household, category, categoryType, unitConversion);
    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/dryer_recommendation")
    public DryerRecommendationDto getDryerRecommendation(String tid, String load) {
        CategoryType categoryType = categoryTypeService.findById(Integer.valueOf(tid));

        UnitConversion unitConversion = unitConversionService.findById(categoryType.getFuel());

        return recommendationService.getDryerRecommendationDto(categoryType, unitConversion, Integer.parseInt(load));
    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/heating_recommendation")
    public HeatingRecommendationDto getHeatingRecommendation(String bid, String cid, String tid) {
        CategoryType categoryType = categoryTypeService.findById(Integer.parseInt(tid));
        BedroomCategory bedroomCategory = bedroomCategoryService.findByBidAndCid(Integer.parseInt(bid),Integer.parseInt(cid));

        UnitConversion unitConversion = unitConversionService.findById(categoryType.getFuel());

        return recommendationService.getHeatingRecommendationDto(categoryType, unitConversion, bedroomCategory);
    }

    @CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
    @GetMapping("/api/average_emission")
    public AverageEmissionCalculationDto getAverageEmissionCalculation(String pid, String bid, String cid) {
        Household household = householdService.findById(Integer.parseInt(pid));
        BedroomCategory bedroomCategory = bedroomCategoryService.findByBidAndCid(Integer.parseInt(bid),Integer.parseInt(cid));

        Double emission = household.getKwhPerYear() * 0.62 * 0.473 + bedroomCategory.getKwhPerYear() * 0.473;

        return new AverageEmissionCalculationDto(emission);
    }

}
