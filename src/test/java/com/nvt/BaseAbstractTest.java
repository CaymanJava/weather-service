package com.nvt;

import com.google.gson.Gson;
import com.nvt.core.data.snapshot.DayTemperatureSnapshot;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static org.apache.commons.io.FileUtils.readFileToString;

public abstract class BaseAbstractTest {

    protected final static String TMP_DIRECTORY = "/tmp/com/nvt";

    protected List<DayTemperatureSnapshot> loadDataFromDisk(String path) {
        return deserializeData(new File(path));
    }

    protected BigDecimal valueOf(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    @SneakyThrows(IOException.class)
    protected void deleteDirectory(String path) {
        FileUtils.deleteDirectory(new File(path));
    }

    @SneakyThrows(FileNotFoundException.class)
    protected List<DayTemperatureSnapshot> loadDataFromResources(String path) {
        var classLoader = getClass().getClassLoader();
        var url = classLoader.getResource(path);
        return ofNullable(url)
                .map(URL::getFile)
                .map(File::new)
                .map(this::deserializeData)
                .orElseThrow(FileNotFoundException::new);
    }

    @SneakyThrows(IOException.class)
    private List<DayTemperatureSnapshot> deserializeData(File file) {
        return asList(new Gson().fromJson(readFileToString(file, "UTF-8"), DayTemperatureSnapshot[].class));
    }

}
