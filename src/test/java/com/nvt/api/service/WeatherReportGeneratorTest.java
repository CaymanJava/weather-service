package com.nvt.api.service;

import com.nvt.BaseAbstractTest;
import com.nvt.PathBuilder;
import com.nvt.api.request.WeatherReportRequest;
import com.nvt.api.response.ReportStatus;
import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import com.nvt.core.report.response.DaysTemperatureReport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeatherReportGeneratorTest extends BaseAbstractTest {

    private WeatherReportGenerator reportGenerator;

    private List<DayTemperatureSnapshot> validDaysTemperature;
    private List<DayTemperatureSnapshot> invalidDaysTemperature;

    private DaysTemperatureReport expectedAllDaysReport;
    private DaysTemperatureReport expectedValidDaysReport;
    private DaysTemperatureReport expectedInvalidDaysReport;

    @Before
    public void init() {
        reportGenerator = new WeatherReportGeneratorFactory().getReportGenerator();

        validDaysTemperature = loadDataFromResources(buildValidDaysTemperaturePath());
        invalidDaysTemperature = loadDataFromResources(buildInvalidDaysTemperaturePath());

        expectedAllDaysReport = prepareExpectedAllDaysReport();
        expectedValidDaysReport = prepareExpectedValidDaysReport();
        expectedInvalidDaysReport = prepareExpectedInvalidDaysReport();
    }

    @After
    public void cleanUp() {
        deleteDirectory(TMP_DIRECTORY);
    }

    @Test
    public void testFromFifthToTwentyFifthDayReport() {
        // given
        var reportRequest = new WeatherReportRequest(5, 25, TMP_DIRECTORY, TMP_DIRECTORY);

        // when
        var weatherReportResponse = reportGenerator.generateReport(reportRequest);

        // then
        assertEquals(ReportStatus.SUCCESS, weatherReportResponse.getStatus());
        assertEquals(validDaysTemperature, loadDataFromDisk(format("%s/%s", TMP_DIRECTORY, weatherReportResponse.getFileReport().getValidDaysFileName())));
        assertEquals(invalidDaysTemperature, loadDataFromDisk(format("%s/%s", TMP_DIRECTORY, weatherReportResponse.getFileReport().getInvalidDaysFileName())));
        assertEquals(expectedAllDaysReport, weatherReportResponse.getDaysReport().getAllDaysReport());
        assertEquals(expectedValidDaysReport, weatherReportResponse.getDaysReport().getValidDaysReport());
        assertEquals(expectedInvalidDaysReport, weatherReportResponse.getDaysReport().getInvalidDaysReport());
    }

    @Test
    public void testOutOfRangeDayReport() {
        // given
        var reportRequest = new WeatherReportRequest(100, 200, TMP_DIRECTORY, TMP_DIRECTORY);

        // when
        var weatherReportResponse = reportGenerator.generateReport(reportRequest);

        // then
        assertEquals(ReportStatus.SUCCESS, weatherReportResponse.getStatus());
        assertTrue(weatherReportResponse.getDaysReport().getAllDaysReport().isEmpty());
        assertTrue(weatherReportResponse.getDaysReport().getValidDaysReport().isEmpty());
        assertTrue(weatherReportResponse.getDaysReport().getInvalidDaysReport().isEmpty());
        assertFalse(weatherReportResponse.getFileReport().isValidDaysFileExist());
        assertFalse(weatherReportResponse.getFileReport().isInvalidDaysFileExist());
    }

    @Test
    public void testFromDayGreaterThanToDay() {
        // given
        var reportRequest = new WeatherReportRequest(100, 50, TMP_DIRECTORY, TMP_DIRECTORY);

        // when
        var weatherReportResponse = reportGenerator.generateReport(reportRequest);

        // then
        assertEquals(ReportStatus.FAILED, weatherReportResponse.getStatus());
        assertEquals("Invalid range! From day should be less than to day parameter. From day: 100, to day: 50", weatherReportResponse.getErrorMessage());
    }

    @Test
    public void testLessThanZeroRange() {
        // given
        var reportRequest = new WeatherReportRequest(-10, 20, TMP_DIRECTORY, TMP_DIRECTORY);

        // when
        var weatherReportResponse = reportGenerator.generateReport(reportRequest);

        // then
        assertEquals(ReportStatus.FAILED, weatherReportResponse.getStatus());
        assertEquals("Invalid range! Incoming parameters should be greater than zero. From day: -10, to day: 20", weatherReportResponse.getErrorMessage());
    }

    private String buildInvalidDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withRange()
                .invalidDataFile()
                .build();
    }

    private String buildValidDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withRange()
                .validDataFile()
                .build();
    }

    private DaysTemperatureReport prepareExpectedAllDaysReport() {
        var daysTemperatureOrdered = loadDataFromResources("data/ordered/range/incoming_data_ordered.json");
        var daysTemperatureReverseOrdered = loadDataFromResources("data/ordered/range/incoming_data_reverse_ordered.json");
        return new DaysTemperatureReport(valueOf(22.82), daysTemperatureOrdered.get(0), daysTemperatureReverseOrdered.get(0),
                daysTemperatureOrdered, daysTemperatureReverseOrdered, 21);
    }

    private DaysTemperatureReport prepareExpectedValidDaysReport() {
        var validDaysTemperatureOrdered = loadDataFromResources("data/ordered/range/valid_data_ordered.json");
        var validDaysTemperatureReverseOrdered = loadDataFromResources("data/ordered/range/valid_data_reverse_ordered.json");
        return new DaysTemperatureReport(valueOf(23.42), validDaysTemperatureOrdered.get(0), validDaysTemperatureReverseOrdered.get(0),
                validDaysTemperatureOrdered, validDaysTemperatureReverseOrdered, 9);
    }

    private DaysTemperatureReport prepareExpectedInvalidDaysReport() {
        var invalidDaysTemperatureOrdered = loadDataFromResources("data/ordered/range/invalid_data_ordered.json");
        var invalidDaysTemperatureReverseOrdered = loadDataFromResources("data/ordered/range/invalid_data_reverse_ordered.json");
        return new DaysTemperatureReport(valueOf(22.38), invalidDaysTemperatureOrdered.get(0), invalidDaysTemperatureReverseOrdered.get(0),
                invalidDaysTemperatureOrdered, invalidDaysTemperatureReverseOrdered, 12);
    }

}
