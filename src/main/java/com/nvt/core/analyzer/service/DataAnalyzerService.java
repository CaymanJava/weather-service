package com.nvt.core.analyzer.service;

import com.nvt.core.analyzer.request.DataAnalyzeRequest;
import com.nvt.core.analyzer.response.DataAnalyzeResponse;
import com.nvt.core.configuration.PropertiesHolder;
import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

@Slf4j
public class DataAnalyzerService implements DataAnalyzer {

    private final PropertiesHolder propertiesHolder = new PropertiesHolder();

    @Override
    public DataAnalyzeResponse analyze(DataAnalyzeRequest request) {
        log.trace("Analyzing data {daySize: {}}", request.getDaysTemperatures().size());
        var response = analyze(request.getDaysTemperatures());
        log.info("Analyzed data {daySize: {}, validDays: {}, invalidDays: {}}",
                request.getDaysTemperatures().size(), response.getValidDays().size(), response.getInvalidDays().size());
        return response;
    }

    private DataAnalyzeResponse analyze(List<DayTemperatureSnapshot> daysTemperatures) {
        if (isEmpty(daysTemperatures)) {
            return DataAnalyzeResponse.empty();
        }
        var validDays = new ArrayList<DayTemperatureSnapshot>();
        var invalidDays = new ArrayList<DayTemperatureSnapshot>();
        daysTemperatures.forEach(dayTemperature -> addToProperlyList(validDays, invalidDays, dayTemperature));
        return new DataAnalyzeResponse(validDays, invalidDays);
    }

    private void addToProperlyList(List<DayTemperatureSnapshot> validDays, List<DayTemperatureSnapshot> invalidDays,
                                   DayTemperatureSnapshot dayTemperature) {
        if (inValidRange(dayTemperature)) {
            validDays.add(dayTemperature);
        } else {
            invalidDays.add(dayTemperature);
        }
    }

    private boolean inValidRange(DayTemperatureSnapshot dayTemperature) {
        return dayTemperature.isBetween(propertiesHolder.getMinDegree(), propertiesHolder.getMaxDegree());
    }

}
