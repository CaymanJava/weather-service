package com.nvt.core.file.service;

import com.nvt.core.file.request.ReportFilesCreateRequest;
import com.nvt.core.file.response.ReportFilesResponse;

public interface ReportFileBuilder {

    ReportFilesResponse build(ReportFilesCreateRequest request);

}
