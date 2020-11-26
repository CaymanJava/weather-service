package com.nvt.core.data.request;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class DayTemperatureFindRequest {

    int fromDay;

    int toDay;

    public int getLimit() {
        return toDay - fromDay + 1;
    }

}
