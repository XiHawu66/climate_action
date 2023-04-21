package com.climate.util;

import com.climate.dto.CountryTrendDto;

import java.util.ArrayList;
import java.util.List;

public class CountryTrendUtil {
    public static CountryTrendDto getCountryTrend(String countryName, List<String[]> lines) {
        String[] title = lines.get(0);
        String[] countryData = lines.stream().filter(data -> data[0].equals(countryName)).findFirst().orElse(null);

        if (countryData == null) {
            return null;
        }

        List<String> nonEmptyTitle = new ArrayList<>();
        List<String> nonEmptyCountryData = new ArrayList<>();

        for (int i = 4; i < countryData.length; i++) {
            if (!countryData[i].equals("")) {
                nonEmptyTitle.add(title[i]);
                nonEmptyCountryData.add(countryData[i]);
            }
        }

        String[] nonEmptyTitleArray = nonEmptyTitle.toArray(new String[0]);
        String[] nonEmptyCountryDataArray = nonEmptyCountryData.toArray(new String[0]);

        return new CountryTrendDto(nonEmptyTitleArray,nonEmptyCountryDataArray);

    }
}
