package com.nvt.core.report.service;

import com.nvt.core.report.request.DaysTemperatureReportRequest;
import com.nvt.core.report.response.DaysTemperatureReportResponse;

public interface DaysTemperatureReportGenerator {

    DaysTemperatureReportResponse generate(DaysTemperatureReportRequest request);

}
