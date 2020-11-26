package com.nvt.core.data.service;

import com.nvt.BaseAbstractTest;
import com.nvt.PathBuilder;
import com.nvt.core.data.request.DayTemperatureFindRequest;
import org.junit.jupiter.api.function.Executable;

public abstract class WeatherDataProviderAbstractTest extends BaseAbstractTest {

    protected String buildDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .incomingDataFile()
                .build();
    }

    protected Executable getTemperatureRange(WeatherDataProvider dataProvider, DayTemperatureFindRequest request) {
        return () -> dataProvider.getTemperature(request);
    }

}
