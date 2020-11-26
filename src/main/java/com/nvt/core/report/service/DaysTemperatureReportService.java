package com.nvt.core.report.service;

import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import com.nvt.core.report.request.DaysTemperatureReportRequest;
import com.nvt.core.report.response.DaysTemperatureReport;
import com.nvt.core.report.response.DaysTemperatureReportResponse;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

@Slf4j
public class DaysTemperatureReportService implements DaysTemperatureReportGenerator {

    @Override
    public DaysTemperatureReportResponse generate(DaysTemperatureReportRequest request) {
        log.trace("Generating report {allDaysSize: {}, validDaysSize: {}, invalidDaysSize: {}}",
                request.getAllDays().size(), request.getValidDays().size(), request.getInvalidDays().size());
        var reportResponse = generateReport(request);
        log.info("Generated report {response: {}", reportResponse);
        return reportResponse;
    }

    private DaysTemperatureReportResponse generateReport(DaysTemperatureReportRequest request) {
        return new DaysTemperatureReportResponse(
                prepareReport(request.getAllDays()),
                prepareReport(request.getValidDays()),
                prepareReport(request.getInvalidDays()));
    }

    private DaysTemperatureReport prepareReport(List<DayTemperatureSnapshot> daysTemperature) {
        return isEmpty(daysTemperature)
                ? DaysTemperatureReport.empty()
                : getReport(daysTemperature);
    }

    private DaysTemperatureReport getReport(List<DayTemperatureSnapshot> daysTemperature) {
        var daysOrderedByTemperature = orderByTemperature(daysTemperature, comparing(DayTemperatureSnapshot::getTemperature));
        var daysReverseOrderedByTemperature = orderByTemperature(daysTemperature, comparing(DayTemperatureSnapshot::getTemperature).reversed());
        return new DaysTemperatureReport(
                calculateAverageTemperature(daysTemperature), daysOrderedByTemperature.get(0),
                daysReverseOrderedByTemperature.get(0), daysOrderedByTemperature,
                daysReverseOrderedByTemperature, daysTemperature.size());
    }

    private List<DayTemperatureSnapshot> orderByTemperature(List<DayTemperatureSnapshot> daysTemperature, Comparator<DayTemperatureSnapshot> comparator) {
        return daysTemperature.stream()
                .filter(Objects::nonNull)
                .sorted(comparator)
                .collect(toList());
    }

    private BigDecimal calculateAverageTemperature(List<DayTemperatureSnapshot> daysTemperature) {
        return daysTemperature.stream()
                .filter(Objects::nonNull)
                .map(DayTemperatureSnapshot::getTemperature)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(daysTemperature.size()), 2, RoundingMode.HALF_UP);
    }

}
