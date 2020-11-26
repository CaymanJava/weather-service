package com.nvt.core.report.service;

import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import com.nvt.core.report.request.DaysTemperatureReportRequest;
import com.nvt.core.report.response.DaysTemperatureReport;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DaysTemperatureReportServiceTest extends DaysTemperatureReportAbstractTest {

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

}
