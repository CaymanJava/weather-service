package com.nvt.core.file.service;

import com.nvt.BaseAbstractTest;
import com.nvt.PathBuilder;
import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import com.nvt.core.file.request.ReportFilesCreateRequest;

import java.util.List;

import static com.nvt.core.file.service.FileType.JSON;

public abstract class ReportFileBuilderAbstractTest extends BaseAbstractTest {

    protected String buildValidDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .validDataFile()
                .build();
    }

    protected String buildInvalidDaysTemperaturePath() {
        return PathBuilder.builder()
                .withOriginal()
                .withAllData()
                .invalidDataFile()
                .build();
    }

    protected ReportFilesCreateRequest buildReportFilesCreateRequest(List<DayTemperatureSnapshot> validDaysTemperature,
                                                                     List<DayTemperatureSnapshot> invalidDaysTemperature) {
        return ReportFilesCreateRequest.builder()
                .validDays(validDaysTemperature)
                .invalidDays(invalidDaysTemperature)
                .validDaysDirectory(TMP_DIRECTORY)
                .invalidDaysDirectory(TMP_DIRECTORY)
                .fileType(JSON)
                .build();
    }

}
