package com.nvt.core.data.client;

import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import feign.RequestLine;

import java.util.List;

interface WeatherMockClient extends WeatherClient {

    @RequestLine("GET /21ae81af-b3a7-46a2-aca3-afa58240ae36")
    List<DayTemperatureSnapshot> getTemperature();

}
