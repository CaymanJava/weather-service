package com.nvt.api.service;

import com.nvt.core.analyzer.service.DataAnalyzerService;
import com.nvt.core.data.service.WeatherDataProviderService;
import com.nvt.core.file.service.ReportFileBuilderService;
import com.nvt.core.report.service.DaysTemperatureReportService;

public class WeatherReportGeneratorFactory {

    public WeatherReportGenerator getReportGenerator() {
        return new WeatherReportGeneratorService(
                new WeatherDataProviderService(), new DataAnalyzerService(),
                new ReportFileBuilderService(), new DaysTemperatureReportService()
        );
    }

}
