package com.nvt.core.file.request;

import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import com.nvt.core.file.service.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

import static java.util.Optional.ofNullable;

@Data
@Builder
@AllArgsConstructor
@ToString(exclude = {"validDays", "invalidDays"})
public class ReportFilesCreateRequest {

    private List<DayTemperatureSnapshot> validDays;

    private List<DayTemperatureSnapshot> invalidDays;

    private String validDaysDirectory;

    private String invalidDaysDirectory;

    private FileType fileType;

    public FileType getFileType() {
        return ofNullable(fileType)
                .orElseGet(FileType::getDefault);
    }

}
