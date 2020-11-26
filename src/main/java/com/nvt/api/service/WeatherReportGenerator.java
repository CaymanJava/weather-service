package com.nvt.api.service;

import com.nvt.api.request.WeatherReportRequest;
import com.nvt.api.response.WeatherReportResponse;

public interface WeatherReportGenerator {

    WeatherReportResponse generateReport(WeatherReportRequest reportRequest);

}
