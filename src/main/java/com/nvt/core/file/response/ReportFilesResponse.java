package com.nvt.core.file.response;

import lombok.AllArgsConstructor;
import lombok.Value;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Value
@AllArgsConstructor
public class ReportFilesResponse {

    String validDaysFileName;

    String invalidDaysFileName;

    public boolean isValidDaysFileExist() {
        return isNotEmpty(validDaysFileName);
    }

    public boolean isInvalidDaysFileExist() {
        return isNotEmpty(invalidDaysFileName);
    }

}
