package com.nvt.core.file.service;

import com.google.gson.Gson;
import com.nvt.core.configuration.PropertiesHolder;
import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import com.nvt.core.file.request.ReportFilesCreateRequest;
import com.nvt.core.file.response.ReportFilesResponse;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.apache.commons.lang.StringUtils.isEmpty;

@Slf4j
class JsonFileBuilder implements ReportFileBuilder {

    private final PropertiesHolder propertiesHolder = new PropertiesHolder();

    @Override
    public ReportFilesResponse build(ReportFilesCreateRequest request) {
        log.trace("Saving report files in JSON format {request: {}}", request);
        var validFileName = saveValidDaysFile(request.getValidDays(), request.getValidDaysDirectory());
        var invalidFileName = saveInvalidDaysFile(request.getInvalidDays(), request.getInvalidDaysDirectory());
        var response = new ReportFilesResponse(validFileName, invalidFileName);
        log.info("Saved report files in JSON format {request: {}, response: {}}", request, response);
        return response;
    }

    private String saveValidDaysFile(List<DayTemperatureSnapshot> daysTemperature, String directory) {
        return saveDaysFile("valid", directory, daysTemperature);
    }

    private String saveInvalidDaysFile(List<DayTemperatureSnapshot> daysTemperature, String directory) {
        return saveDaysFile("invalid", directory, daysTemperature);
    }

    private String saveDaysFile(String prefix, String directory, List<DayTemperatureSnapshot> daysTemperature) {
        return isNotEmpty(daysTemperature)
                ? saveFile(prefix, directory, daysTemperature)
                : null;
    }

    @SneakyThrows(IOException.class)
    private String saveFile(String prefix, String directory, List<DayTemperatureSnapshot> daysTemperature) {
        var fileName = createFileName(prefix);
        writeStringToFile(buildFile(directory, fileName), new Gson().toJson(daysTemperature), Charset.defaultCharset());
        return fileName;
    }

    private String createFileName(@NonNull String prefix) {
        return format("%s-%s.json", prefix, randomUUID().toString());
    }

    private File buildFile(String directory, String fileName) {
        return new File(buildFilePath(directory, fileName));
    }

    private String buildFilePath(String directory, String fileName) {
        return format("%s%s", formatDirectory(directory), fileName);
    }

    private String formatDirectory(String directory) {
        if (isEmpty(directory)) {
            return propertiesHolder.getDefaultDirectory();
        }
        return directory.endsWith("/")
                ? directory
                : format("%s/", directory);
    }

}
