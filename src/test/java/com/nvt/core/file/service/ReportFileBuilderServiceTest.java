package com.nvt.core.file.service;

import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReportFileBuilderServiceTest extends ReportFileBuilderAbstractTest {

    private ReportFileBuilder fileBuilder;
    private List<DayTemperatureSnapshot> validDaysTemperature;
    private List<DayTemperatureSnapshot> invalidDaysTemperature;

    @Before
    public void init() {
        fileBuilder = new ReportFileBuilderService();
        validDaysTemperature = loadDataFromResources(buildValidDaysTemperaturePath());
        invalidDaysTemperature = loadDataFromResources(buildInvalidDaysTemperaturePath());
    }

    @After
    public void cleanUp() {
        deleteDirectory(TMP_DIRECTORY);
    }

    @Test
    public void testFullDataSetJsonFileCreation() {
        // given
        var fileCreateRequest = buildReportFilesCreateRequest(validDaysTemperature, invalidDaysTemperature);

        // when
        var reportFilesResponse = fileBuilder.build(fileCreateRequest);

        // then
        assertEquals(validDaysTemperature, loadDataFromDisk(format("%s/%s", TMP_DIRECTORY, reportFilesResponse.getValidDaysFileName())));
        assertEquals(invalidDaysTemperature, loadDataFromDisk(format("%s/%s", TMP_DIRECTORY, reportFilesResponse.getInvalidDaysFileName())));
    }

    @Test
    public void testOnlyValidDataSetJsonFileCreation() {
        // given
        var fileCreateRequest = buildReportFilesCreateRequest(validDaysTemperature, null);

        // when
        var reportFilesResponse = fileBuilder.build(fileCreateRequest);

        // then
        assertEquals(validDaysTemperature, loadDataFromDisk(format("%s/%s", TMP_DIRECTORY, reportFilesResponse.getValidDaysFileName())));
        assertFalse(reportFilesResponse.isInvalidDaysFileExist());
    }

    @Test
    public void testOnlyInvalidDataSetJsonFileCreation() {
        // given
        var fileCreateRequest = buildReportFilesCreateRequest(null, invalidDaysTemperature);

        // when
        var reportFilesResponse = fileBuilder.build(fileCreateRequest);

        // then
        assertEquals(invalidDaysTemperature, loadDataFromDisk(format("%s/%s", TMP_DIRECTORY, reportFilesResponse.getInvalidDaysFileName())));
        assertFalse(reportFilesResponse.isValidDaysFileExist());
    }

    @Test
    public void testEmptyDataSetJsonFileCreation() {
        // given
        var fileCreateRequest = buildReportFilesCreateRequest(null, null);

        // when
        var reportFilesResponse = fileBuilder.build(fileCreateRequest);

        // then
        assertFalse(reportFilesResponse.isValidDaysFileExist());
        assertFalse(reportFilesResponse.isInvalidDaysFileExist());
    }

}
