package com.nvt.api.service;

import com.nvt.api.request.WeatherReportRequest;
import com.nvt.api.response.WeatherReportResponse;
import com.nvt.core.analyzer.request.DataAnalyzeRequest;
import com.nvt.core.analyzer.response.DataAnalyzeResponse;
import com.nvt.core.analyzer.service.DataAnalyzer;
import com.nvt.core.data.request.DayTemperatureFindRequest;
import com.nvt.core.data.service.WeatherDataProvider;
import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import com.nvt.core.file.request.ReportFilesCreateRequest;
import com.nvt.core.file.service.ReportFileBuilder;
import com.nvt.core.report.request.DaysTemperatureReportRequest;
import com.nvt.core.report.service.DaysTemperatureReportGenerator;
import com.nvt.core.util.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.nvt.api.response.WeatherReportResponse.failed;
import static com.nvt.api.response.WeatherReportResponse.success;
import static com.nvt.core.file.service.FileType.JSON;
import static lombok.AccessLevel.PACKAGE;

@Slf4j
@AllArgsConstructor(access = PACKAGE)
class WeatherReportGeneratorService implements WeatherReportGenerator {

    private final WeatherDataProvider dataProvider;
    private final DataAnalyzer dataAnalyzer;
    private final ReportFileBuilder reportFileBuilder;
    private final DaysTemperatureReportGenerator reportGenerator;

    @Override
    public WeatherReportResponse generateReport(WeatherReportRequest reportRequest) {
        log.trace("Generating weather report {request: {}}", reportRequest);
        var reportResponse = tryGenerateReport(reportRequest);
        log.info("Generated weather report {request: {}, response: {}}", reportRequest, reportResponse);
        return reportResponse;
    }

    private WeatherReportResponse tryGenerateReport(WeatherReportRequest reportRequest) {
        try {
            var daysTemperaturePage = dataProvider.getTemperature(buildDayTemperatureFindRequest(reportRequest));
            var analyzeResponse = dataAnalyzer.analyze(new DataAnalyzeRequest(daysTemperaturePage.getData()));
            var reportFilesResponse = reportFileBuilder.build(buildReportFilesCreateRequest(reportRequest, analyzeResponse));
            var reportResponse = reportGenerator.generate(buildDaysTemperatureReportRequest(daysTemperaturePage, analyzeResponse));
            return success(reportFilesResponse, reportResponse);
        } catch (Exception exception) {
            log.warn("Report generation has failed {message: {}}", exception.getMessage(), exception);
            return failed(exception.getMessage());
        }
    }

    private DayTemperatureFindRequest buildDayTemperatureFindRequest(WeatherReportRequest reportRequest) {
        return new DayTemperatureFindRequest(reportRequest.getFromDay(), reportRequest.getToDay());
    }

    private ReportFilesCreateRequest buildReportFilesCreateRequest(WeatherReportRequest reportRequest, DataAnalyzeResponse analyzeResponse) {
        return new ReportFilesCreateRequest(analyzeResponse.getValidDays(), analyzeResponse.getInvalidDays(),
                reportRequest.getValidReportDirectoryPath(), reportRequest.getInvalidReportDirectoryPath(), JSON);
    }

    private DaysTemperatureReportRequest buildDaysTemperatureReportRequest(Page<DayTemperatureSnapshot> daysTemperaturePage, DataAnalyzeResponse analyzeResponse) {
        return new DaysTemperatureReportRequest(daysTemperaturePage.getData(), analyzeResponse.getValidDays(), analyzeResponse.getInvalidDays());
    }

}
