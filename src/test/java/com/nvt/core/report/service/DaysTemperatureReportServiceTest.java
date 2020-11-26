package com.nvt.core.report.service;

import com.nvt.BaseAbstractTest;
import com.nvt.PathBuilder;
import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import com.nvt.core.report.request.DaysTemperatureReportRequest;
import com.nvt.core.report.response.DaysTemperatureReport;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DaysTemperatureReportServiceTest extends BaseAbstractTest {

    private DaysTemperatureReportGenerator daysTemperatureReportGenerator;

    private List<DayTemperatureSnapshot> daysTemperature;
    private List<DayTemperatureSnapshot> validDaysTemperature;
    private List<DayTemperatureSnapshot> invalidDaysTemperature;

    private DaysTemperatureReport expectedAllDaysReport;
    private DaysTemperatureReport expectedValidDaysReport;
    private DaysTemperatureReport expectedInvalidDaysReport;

    @Before
    public void init() {
        daysTemperatureReportGenerator = new DaysTemperatureReportService();

        daysTemperature = loadDataFromResources(buildDaysTemperaturePath());
        validDaysTemperature = loadDataFromResources(buildValidDaysTemperaturePath());
        invalidDaysTemperature = loadDataFromResources(buildInvalidDaysTemperaturePath());

        expectedAllDaysReport = prepareExpectedAllDaysReport();
        expectedValidDaysReport = prepareExpectedValidDaysReport();
        expectedInvalidDaysReport = prepareExpectedInvalidDaysReport();
    }

    @Test
    public void testAllDataGenerationReport() {
        // given
        var reportRequest = new DaysTemperatureReportRequest(daysTemperature, validDaysTemperature, invalidDaysTemperature);

        // when
        var reportResponse = daysTemperatureReportGenerator.generate(reportRequest);

        // then
        assertEquals(expectedAllDaysReport, reportResponse.getAllDaysReport());
        assertEquals(expectedValidDaysReport, reportResponse.getValidDaysReport());
        assertEquals(expectedInvalidDaysReport, reportResponse.getInvalidDaysReport());
    }

    @Test
    public void testAllDaysAndValidDaysGenerationReport() {
        // given
        var reportRequest = new DaysTemperatureReportRequest(daysTemperature, validDaysTemperature, null);

        // when
        var reportResponse = daysTemperatureReportGenerator.generate(reportRequest);

        // then
        assertEquals(expectedAllDaysReport, reportResponse.getAllDaysReport());
        assertEquals(expectedValidDaysReport, reportResponse.getValidDaysReport());
        assertTrue(reportResponse.getInvalidDaysReport().isEmpty());
    }

    @Test
    public void testAllDaysAndInvalidDaysGenerationReport() {
        // given
        var reportRequest = new DaysTemperatureReportRequest(daysTemperature, null, invalidDaysTemperature);

        // when
        var reportResponse = daysTemperatureReportGenerator.generate(reportRequest);

        // then
        assertEquals(expectedAllDaysReport, reportResponse.getAllDaysReport());
        assertTrue(reportResponse.getValidDaysReport().isEmpty());
        assertEquals(expectedInvalidDaysReport, reportResponse.getInvalidDaysReport());
    }

    @Test
    public void testValidAndInvalidDaysGenerationReport() {
        // given
        var reportRequest = new DaysTemperatureReportRequest(null, validDaysTemperature, invalidDaysTemperature);

        // when
        var reportResponse = daysTemperatureReportGenerator.generate(reportRequest);

        // then
        assertTrue(reportResponse.getAllDaysReport().isEmpty());
        assertEquals(expectedValidDaysReport, reportResponse.getValidDaysReport());
        assertEquals(expectedInvalidDaysReport, reportResponse.getInvalidDaysReport());
    }

    @Test
    public void testEmptyDataGenerationReport() {
        // given
        var reportRequest = new DaysTemperatureReportRequest(null, null, null);

        // when
        var reportResponse = daysTemperatureReportGenerator.generate(reportRequest);

        // then
        assertTrue(reportResponse.getAllDaysReport().isEmpty());
        assertTrue(reportResponse.getValidDaysReport().isEmpty());
        assertTrue(reportResponse.getInvalidDaysReport().isEmpty());
    }

    private String buildDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .incomingDataFile().build();
    }

    private String buildValidDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .validDataFile()
                .build();
    }

    private String buildInvalidDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .invalidDataFile()
                .build();
    }

    private DaysTemperatureReport prepareExpectedAllDaysReport() {
        var daysTemperatureOrdered = loadDataFromResources(buildDaysTemperatureOrderedPath());
        var daysTemperatureReverseOrdered = loadDataFromResources(buildDaysTemperatureReverseOrderedPath());
        return new DaysTemperatureReport(valueOf(22.9), daysTemperatureOrdered.get(0), daysTemperatureReverseOrdered.get(0),
                daysTemperatureOrdered, daysTemperatureReverseOrdered, 30);
    }

    private String buildDaysTemperatureOrderedPath() {
        return PathBuilder.builder()
                .withOrdering()
                .withAllData()
                .incomingDataFile()
                .build();
    }

    private String buildDaysTemperatureReverseOrderedPath() {
        return PathBuilder.builder()
                .withOrdering()
                .reverse()
                .withAllData()
                .incomingDataFile()
                .build();
    }

    private DaysTemperatureReport prepareExpectedValidDaysReport() {
        var validDaysTemperatureOrdered = loadDataFromResources(buildValidDaysTemperatureOrderedPath());
        var validDaysTemperatureReverseOrdered = loadDataFromResources(buildValidDaysTemperatureReverseOrderedPath());
        return new DaysTemperatureReport(valueOf(23.44), validDaysTemperatureOrdered.get(0), validDaysTemperatureReverseOrdered.get(0),
                validDaysTemperatureOrdered, validDaysTemperatureReverseOrdered, 13);
    }

    private String buildValidDaysTemperatureOrderedPath() {
        return PathBuilder.builder()
                .withOrdering()
                .withAllData()
                .validDataFile()
                .build();
    }

    private String buildValidDaysTemperatureReverseOrderedPath() {
        return PathBuilder.builder()
                .withOrdering()
                .withAllData()
                .reverse()
                .validDataFile()
                .build();
    }

    private DaysTemperatureReport prepareExpectedInvalidDaysReport() {
        var invalidDaysTemperatureOrdered = loadDataFromResources(buildInvalidDaysTemperatureOrderedPath());
        var invalidDaysTemperatureReverseOrdered = loadDataFromResources(buildInvalidDaysTemperatureReverseOrderedPath());
        return new DaysTemperatureReport(valueOf(22.49), invalidDaysTemperatureOrdered.get(0), invalidDaysTemperatureReverseOrdered.get(0),
                invalidDaysTemperatureOrdered, invalidDaysTemperatureReverseOrdered, 17);
    }

    private String buildInvalidDaysTemperatureOrderedPath() {
        return PathBuilder.builder()
                .withOrdering()
                .withAllData()
                .invalidDataFile()
                .build();
    }

    private String buildInvalidDaysTemperatureReverseOrderedPath() {
        return PathBuilder
                .builder()
                .withOrdering()
                .withAllData()
                .reverse()
                .invalidDataFile()
                .build();
    }

}
