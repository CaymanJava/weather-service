package com.nvt.core.report.service;

import com.nvt.BaseAbstractTest;
import com.nvt.PathBuilder;
import com.nvt.core.report.response.DaysTemperatureReport;

public abstract class DaysTemperatureReportAbstractTest extends BaseAbstractTest {

    protected String buildDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .incomingDataFile().build();
    }

    protected String buildValidDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .validDataFile()
                .build();
    }

    protected String buildInvalidDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .invalidDataFile()
                .build();
    }

    protected DaysTemperatureReport prepareExpectedAllDaysReport() {
        var daysTemperatureOrdered = loadDataFromResources(buildDaysTemperatureOrderedPath());
        var daysTemperatureReverseOrdered = loadDataFromResources(buildDaysTemperatureReverseOrderedPath());
        return new DaysTemperatureReport(valueOf(22.9), daysTemperatureOrdered.get(0), daysTemperatureReverseOrdered.get(0),
                daysTemperatureOrdered, daysTemperatureReverseOrdered, 30);
    }

    protected DaysTemperatureReport prepareExpectedValidDaysReport() {
        var validDaysTemperatureOrdered = loadDataFromResources(buildValidDaysTemperatureOrderedPath());
        var validDaysTemperatureReverseOrdered = loadDataFromResources(buildValidDaysTemperatureReverseOrderedPath());
        return new DaysTemperatureReport(valueOf(23.44), validDaysTemperatureOrdered.get(0), validDaysTemperatureReverseOrdered.get(0),
                validDaysTemperatureOrdered, validDaysTemperatureReverseOrdered, 13);
    }

    protected DaysTemperatureReport prepareExpectedInvalidDaysReport() {
        var invalidDaysTemperatureOrdered = loadDataFromResources(buildInvalidDaysTemperatureOrderedPath());
        var invalidDaysTemperatureReverseOrdered = loadDataFromResources(buildInvalidDaysTemperatureReverseOrderedPath());
        return new DaysTemperatureReport(valueOf(22.49), invalidDaysTemperatureOrdered.get(0), invalidDaysTemperatureReverseOrdered.get(0),
                invalidDaysTemperatureOrdered, invalidDaysTemperatureReverseOrdered, 17);
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
