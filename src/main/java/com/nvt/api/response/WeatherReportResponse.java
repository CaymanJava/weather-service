package com.nvt.api.response;

import com.nvt.core.file.response.ReportFilesResponse;
import com.nvt.core.report.response.DaysTemperatureReportResponse;
import lombok.AllArgsConstructor;
import lombok.Value;

import static com.nvt.api.response.ReportStatus.FAILED;
import static com.nvt.api.response.ReportStatus.SUCCESS;
import static lombok.AccessLevel.PRIVATE;

@Value
@AllArgsConstructor(access = PRIVATE)
public class WeatherReportResponse {

    ReportStatus status;

    String errorMessage;

    ReportFilesResponse fileReport;

    DaysTemperatureReportResponse daysReport;

    public static WeatherReportResponse success(ReportFilesResponse fileReport, DaysTemperatureReportResponse daysReport) {
        return new WeatherReportResponse(SUCCESS, "", fileReport, daysReport);
    }

    public static WeatherReportResponse failed(String errorMessage) {
        return new WeatherReportResponse(FAILED, errorMessage, null, null);
    }

}
