package com.nvt.core.analyzer.service;

import com.nvt.BaseAbstractTest;
import com.nvt.PathBuilder;

public abstract class DataAnalyzerAbstractTest extends BaseAbstractTest {

    protected String buildInvalidDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .invalidDataFile()
                .build();
    }

    protected String buildValidDaysTemperaturePath() {
        return PathBuilder
                .builder()
                .withOriginal()
                .withAllData()
                .validDataFile()
                .build();
    }

    protected String buildDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .incomingDataFile()
                .build();
    }

}
