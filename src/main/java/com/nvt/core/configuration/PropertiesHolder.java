package com.nvt.core.configuration;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.math.BigDecimal;

@Value
public class PropertiesHolder {

    private final static String PROPERTIES_FILENAME = "application.properties";

    PropertiesConfiguration config;

    @SneakyThrows(ConfigurationException.class)
    public PropertiesHolder() {
        config = new PropertiesConfiguration();
        config.load(PROPERTIES_FILENAME);
    }

    public BigDecimal getMaxDegree() {
        return config.getBigDecimal("max-degree");
    }

    public BigDecimal getMinDegree() {
        return config.getBigDecimal("min-degree");
    }

    public String getDefaultDirectory() {
        return config.getString("default-directory");
    }

}
