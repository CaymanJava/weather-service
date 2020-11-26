package com.nvt.core.analyzer.service;

import com.nvt.core.analyzer.request.DataAnalyzeRequest;
import com.nvt.core.analyzer.response.DataAnalyzeResponse;

public interface DataAnalyzer {

    DataAnalyzeResponse analyze(DataAnalyzeRequest request);

}
