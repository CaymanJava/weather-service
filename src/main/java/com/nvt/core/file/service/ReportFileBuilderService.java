package com.nvt.core.file.service;

import com.nvt.core.file.request.ReportFilesCreateRequest;
import com.nvt.core.file.response.ReportFilesResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportFileBuilderService implements ReportFileBuilder {

    @Override
    public ReportFilesResponse build(ReportFilesCreateRequest request) {
        log.trace("Saving report files {request: {}}", request);
        var response = request.getFileType().getBuilder().build(request);
        log.info("Saved report files {request: {}, response: {}}", request, response);
        return response;
    }

}
