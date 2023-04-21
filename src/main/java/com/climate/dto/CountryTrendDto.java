package com.climate.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class CountryTrendDto implements Serializable {
    private String[] titles;

    private String[] countryData;

    public CountryTrendDto(String[] titles, String[] countryData) {
        this.titles = titles;
        this.countryData = countryData;
    }

}
