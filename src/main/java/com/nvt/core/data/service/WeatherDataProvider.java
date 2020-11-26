package com.nvt.core.data.service;

import com.nvt.core.data.request.DayTemperatureFindRequest;
import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import com.nvt.core.util.Page;

public interface WeatherDataProvider {

    Page<DayTemperatureSnapshot> getTemperature(DayTemperatureFindRequest request);

}
