package com.nvt.core.data.service;

import com.nvt.BaseAbstractTest;
import com.nvt.PathBuilder;
import com.nvt.core.data.excpetion.InvalidRangeException;
import com.nvt.core.data.request.DayTemperatureFindRequest;
import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeatherDataProviderTest extends BaseAbstractTest {

    private WeatherDataProvider dataProvider;
    private List<DayTemperatureSnapshot> daysTemperature;

    @Before
    public void init() {
        dataProvider = new WeatherDataProviderService();
        daysTemperature = loadDataFromResources(buildDaysTemperaturePath());
    }

    @Test
    public void testRangeMoreThanDataSize() {
        // given
        var findRequest = new DayTemperatureFindRequest(30, 32);

        // when
        var daysTemperaturePage = dataProvider.getTemperature(findRequest);

        // then
        assertEquals(daysTemperature.subList(29, 30), daysTemperaturePage.getData());
        assertEquals(30, daysTemperaturePage.getOffSet());
        assertEquals(3, daysTemperaturePage.getLimit());
        assertEquals(1, daysTemperaturePage.getTotal());
    }

    @Test
    public void testOutOfRange() {
        // given
        var findRequest = new DayTemperatureFindRequest(54, 55);

        // when
        var daysTemperaturePage = dataProvider.getTemperature(findRequest);

        // then
        assertTrue(daysTemperaturePage.getData().isEmpty());
        assertEquals(54, daysTemperaturePage.getOffSet());
        assertEquals(2, daysTemperaturePage.getLimit());
        assertEquals(0, daysTemperaturePage.getTotal());
    }

    @Test
    public void testFullData() {
        // given
        var findRequest = new DayTemperatureFindRequest(1, 100);

        // when
        var daysTemperaturePage = dataProvider.getTemperature(findRequest);

        // then
        assertEquals(daysTemperature, daysTemperaturePage.getData());
        assertEquals(1, daysTemperaturePage.getOffSet());
        assertEquals(100, daysTemperaturePage.getLimit());
        assertEquals(30, daysTemperaturePage.getTotal());
    }

    @Test
    public void testFromFifthToTenthDays() {
        // given
        var findRequest = new DayTemperatureFindRequest(5, 10);

        // when
        var daysTemperaturePage = dataProvider.getTemperature(findRequest);

        // then
        assertEquals(daysTemperature.subList(4, 10), daysTemperaturePage.getData());
        assertEquals(5, daysTemperaturePage.getOffSet());
        assertEquals(6, daysTemperaturePage.getLimit());
        assertEquals(6, daysTemperaturePage.getTotal());
    }

    @Test
    public void testFromTenthToTwentyFifthDays() {
        // given
        var findRequest = new DayTemperatureFindRequest(10, 25);

        // when
        var daysTemperaturePage = dataProvider.getTemperature(findRequest);

        // then
        assertEquals(daysTemperature.subList(9, 25), daysTemperaturePage.getData());
        assertEquals(10, daysTemperaturePage.getOffSet());
        assertEquals(16, daysTemperaturePage.getLimit());
        assertEquals(16, daysTemperaturePage.getTotal());
    }

    @Test
    public void testLessThanZeroRange() {
        // given
        var findRequest = new DayTemperatureFindRequest(-1, 2);

        // when
        var gettingRangeFunction = getTemperatureRange(findRequest);

        // then
        assertThrows(InvalidRangeException.class, gettingRangeFunction, "Invalid range! Incoming parameters should be greater than zero. From day: -1, to day: 2");
    }

    @Test
    public void testFromDayGreaterThanToDay() {
        // given
        var findRequest = new DayTemperatureFindRequest(7, 2);

        // when
        var gettingRangeFunction = getTemperatureRange(findRequest);

        // then
        assertThrows(InvalidRangeException.class, gettingRangeFunction, "Invalid range! Incoming parameters should be greater than zero. From day: -1, to day: 2");
    }

    private String buildDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .incomingDataFile()
                .build();
    }

    private Executable getTemperatureRange(DayTemperatureFindRequest request) {
        return () -> dataProvider.getTemperature(request);
    }

}
