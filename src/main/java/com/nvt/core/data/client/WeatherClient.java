package com.nvt.core.data.client;

import com.nvt.core.data.snapshot.DayTemperatureSnapshot;

import java.util.List;

public interface WeatherClient {

    List<DayTemperatureSnapshot> getTemperature();

}
