package com.climate.util;

import com.climate.dto.CountryTrendDto;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class CSVUtil {
    public static List<String[]> readCSV(String path) {
        Resource resource = new ClassPathResource(path);

        List<String[]> lines = null;

        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(resource.getInputStream()))).build()) {
            lines = csvReader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lines;
    }

}
