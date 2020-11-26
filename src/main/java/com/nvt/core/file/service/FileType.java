package com.nvt.core.file.service;

import lombok.Getter;

public enum FileType {

    JSON(new JsonFileBuilder());

    @Getter
    private final ReportFileBuilder builder;

    FileType(ReportFileBuilder builder) {
        this.builder = builder;
    }

    public static FileType getDefault() {
        return JSON;
    }

}
