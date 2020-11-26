package com.nvt.core.data.snapshot;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.util.Objects.isNull;

@Value
@AllArgsConstructor
public class DayTemperatureSnapshot {

    LocalDate date;

    BigDecimal temperature;

    public boolean isBetween(BigDecimal minTemperature, BigDecimal maxTemperature) {
        if (isNull(temperature) || isNull(minTemperature) || isNull(maxTemperature)) {
            return false;
        }
        return temperature.compareTo(minTemperature) >= 0 && temperature.compareTo(maxTemperature) <= 0;
    }

}
