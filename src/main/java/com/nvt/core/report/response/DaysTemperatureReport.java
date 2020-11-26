package com.nvt.core.report.response;

import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@AllArgsConstructor
@ToString(exclude = {"daysOrderedByTemperature", "daysReverseOrderedByTemperature"})
public class DaysTemperatureReport {

    BigDecimal averageTemperature;

    DayTemperatureSnapshot minDayTemperature;

    DayTemperatureSnapshot maxDayTemperature;

    List<DayTemperatureSnapshot> daysOrderedByTemperature;

    List<DayTemperatureSnapshot> daysReverseOrderedByTemperature;

    int daysCount;

    private DaysTemperatureReport() {
        this.averageTemperature = null;
        this.maxDayTemperature = null;
        this.minDayTemperature = null;
        this.daysOrderedByTemperature = null;
        this.daysReverseOrderedByTemperature = null;
        this.daysCount = 0;
    }

    public static DaysTemperatureReport empty() {
        return new DaysTemperatureReport();
    }

    public boolean isEmpty() {
        return daysCount == 0;
    }

}
