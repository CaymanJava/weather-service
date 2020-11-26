package com.nvt.core.analyzer.response;

import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Value
@AllArgsConstructor
public class DataAnalyzeResponse {

    List<DayTemperatureSnapshot> validDays;

    List<DayTemperatureSnapshot> invalidDays;

    public static DataAnalyzeResponse empty() {
        return new DataAnalyzeResponse(emptyList(), emptyList());
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(validDays) && CollectionUtils.isEmpty(invalidDays);
    }

    public List<DayTemperatureSnapshot> getValidDays() {
        return ofNullable(validDays)
                .orElseGet(Collections::emptyList);
    }

    public List<DayTemperatureSnapshot> getInvalidDays() {
        return ofNullable(invalidDays)
                .orElseGet(Collections::emptyList);
    }

}
