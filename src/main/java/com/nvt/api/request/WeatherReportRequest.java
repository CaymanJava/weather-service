package com.nvt.api.request;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class WeatherReportRequest {

    int fromDay;

    int toDay;

    String validReportDirectoryPath;

    String invalidReportDirectoryPath;

}
