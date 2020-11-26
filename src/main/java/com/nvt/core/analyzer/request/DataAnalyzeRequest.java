package com.nvt.core.analyzer.request;

import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;

@Value
@AllArgsConstructor
public class DataAnalyzeRequest {

    List<DayTemperatureSnapshot> daysTemperatures;

    public List<DayTemperatureSnapshot> getDaysTemperatures() {
        return ofNullable(daysTemperatures)
                .orElseGet(Collections::emptyList);
    }

}
