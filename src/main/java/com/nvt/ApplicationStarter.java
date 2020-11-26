package com.nvt;

import com.nvt.api.request.WeatherReportRequest;
import com.nvt.api.service.WeatherReportGeneratorFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationStarter {

    public static void main(String[] args) {
        var directory = "/tmp/com/nvt/";
        var reportGenerator = new WeatherReportGeneratorFactory().getReportGenerator();
        var reportResponse = reportGenerator.generateReport(new WeatherReportRequest(5, 25, directory, directory));
        log.info("Report: {}", reportResponse);
    }

}
