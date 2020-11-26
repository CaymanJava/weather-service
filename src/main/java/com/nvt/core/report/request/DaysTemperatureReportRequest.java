package com.nvt.core.report.request;

import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

import static com.nvt.core.util.CollectionUtils.getOrElseEmpty;

@Value
@AllArgsConstructor
public class DaysTemperatureReportRequest {

    List<DayTemperatureSnapshot> allDays;

    List<DayTemperatureSnapshot> validDays;

    List<DayTemperatureSnapshot> invalidDays;

    public List<DayTemperatureSnapshot> getAllDays() {
        return getOrElseEmpty(allDays);
    }

    public List<DayTemperatureSnapshot> getValidDays() {
        return getOrElseEmpty(validDays);
    }

    public List<DayTemperatureSnapshot> getInvalidDays() {
        return getOrElseEmpty(invalidDays);
    }

}
