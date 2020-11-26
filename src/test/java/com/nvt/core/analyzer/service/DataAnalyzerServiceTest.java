package com.nvt.core.analyzer.service;

import com.nvt.BaseAbstractTest;
import com.nvt.PathBuilder;
import com.nvt.core.analyzer.request.DataAnalyzeRequest;
import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataAnalyzerServiceTest extends BaseAbstractTest {

    private DataAnalyzer dataAnalyzer;
    private List<DayTemperatureSnapshot> daysTemperature;
    private List<DayTemperatureSnapshot> validDaysTemperature;
    private List<DayTemperatureSnapshot> invalidDaysTemperature;

    @Before
    public void init() {
        dataAnalyzer = new DataAnalyzerService();
        daysTemperature = loadDataFromResources(buildDaysTemperaturePath());
        validDaysTemperature = loadDataFromResources(buildValidDaysTemperaturePath());
        invalidDaysTemperature = loadDataFromResources(buildInvalidDaysTemperaturePath());
    }

    @Test
    public void testAllDaysTemperature() {
        // given
        var analyzeRequest = new DataAnalyzeRequest(daysTemperature);

        // when
        var dataAnalyzeResponse = dataAnalyzer.analyze(analyzeRequest);

        // then
        assertEquals(validDaysTemperature, dataAnalyzeResponse.getValidDays());
        assertEquals(invalidDaysTemperature, dataAnalyzeResponse.getInvalidDays());
    }

    @Test
    public void testAllValidDays() {
        // given
        var analyzeRequest = new DataAnalyzeRequest(validDaysTemperature);

        // when
        var dataAnalyzeResponse = dataAnalyzer.analyze(analyzeRequest);

        // then
        assertEquals(validDaysTemperature, dataAnalyzeResponse.getValidDays());
        assertTrue(dataAnalyzeResponse.getInvalidDays().isEmpty());
    }

    @Test
    public void testAllInvalidDays() {
        // given
        var analyzeRequest = new DataAnalyzeRequest(invalidDaysTemperature);

        // when
        var dataAnalyzeResponse = dataAnalyzer.analyze(analyzeRequest);

        // then
        assertEquals(invalidDaysTemperature, dataAnalyzeResponse.getInvalidDays());
        assertTrue(dataAnalyzeResponse.getValidDays().isEmpty());
    }

    @Test
    public void testEmptyDaysRequest() {
        // given
        var analyzeRequest = new DataAnalyzeRequest(null);

        // when
        var dataAnalyzeResponse = dataAnalyzer.analyze(analyzeRequest);

        // then
        assertTrue(dataAnalyzeResponse.isEmpty());
    }

    private String buildInvalidDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .invalidDataFile()
                .build();
    }

    private String buildValidDaysTemperaturePath() {
        return PathBuilder
                .builder()
                .withOriginal()
                .withAllData()
                .validDataFile()
                .build();
    }

    private String buildDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .incomingDataFile()
                .build();
    }

}
