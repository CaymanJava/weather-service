package com.nvt.core.data.service;

import com.nvt.core.data.client.WeatherClient;
import com.nvt.core.data.client.WeatherClientFactory;
import com.nvt.core.data.excpetion.InvalidRangeException;
import com.nvt.core.data.request.DayTemperatureFindRequest;
import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import com.nvt.core.util.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.nvt.core.util.Page.empty;
import static com.nvt.core.util.Page.of;
import static java.lang.String.format;

@Slf4j
@AllArgsConstructor
public class WeatherDataProviderService implements WeatherDataProvider {

    private final WeatherClient weatherClient;

    public WeatherDataProviderService() {
        this.weatherClient = WeatherClientFactory.getClient();
    }

    @Override
    public Page<DayTemperatureSnapshot> getTemperature(DayTemperatureFindRequest request) {
        log.trace("Getting temperature in range {request: {}}", request);
        checkRange(request);
        var daysTemperature = weatherClient.getTemperature();
        Page<DayTemperatureSnapshot> daysTemperaturePage = filterData(daysTemperature, request);
        log.info("Got temperature in range {request: {}, dataSize: {}}", request, daysTemperaturePage.getData().size());
        return daysTemperaturePage;
    }

    private void checkRange(DayTemperatureFindRequest request) {
        if (isIncomingDataLessThanZero(request)) {
            logAndThrowException(format("Invalid range! Incoming parameters should be greater than zero. From day: %d, to day: %d", request.getFromDay(), request.getToDay()));
        }

        if (isFromDayGreaterThaToDay(request)) {
            logAndThrowException(format("Invalid range! From day should be less than to day parameter. From day: %d, to day: %d", request.getFromDay(), request.getToDay()));
        }
    }

    private void logAndThrowException(String message) {
        log.warn(message);
        throw new InvalidRangeException(message);
    }

    private boolean isIncomingDataLessThanZero(DayTemperatureFindRequest request) {
        return request.getFromDay() <= 0 || request.getToDay() <= 0;
    }

    private boolean isFromDayGreaterThaToDay(DayTemperatureFindRequest request) {
        return request.getFromDay() >= request.getToDay();
    }

    private Page<DayTemperatureSnapshot> filterData(List<DayTemperatureSnapshot> daysTemperature, DayTemperatureFindRequest request) {
        if (isOutOfRange(daysTemperature, request)) {
            return empty(request.getFromDay(), request.getLimit());
        }

        if (isRangeMoreThanDataSize(daysTemperature, request)) {
            return of(daysTemperature.subList(request.getFromDay() - 1, daysTemperature.size()), request.getFromDay(), request.getLimit());
        }

        return of(daysTemperature.subList(request.getFromDay() - 1, request.getToDay()), request.getFromDay(), request.getLimit());
    }

    private boolean isOutOfRange(List<DayTemperatureSnapshot> daysTemperature, DayTemperatureFindRequest request) {
        return request.getFromDay() - 1 > daysTemperature.size();
    }

    private boolean isRangeMoreThanDataSize(List<DayTemperatureSnapshot> daysTemperature, DayTemperatureFindRequest request) {
        return request.getToDay() >= daysTemperature.size();
    }

}
