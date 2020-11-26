package com.nvt.core.report.response;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class DaysTemperatureReportResponse {

    DaysTemperatureReport allDaysReport;

    DaysTemperatureReport validDaysReport;

    DaysTemperatureReport invalidDaysReport;

}
