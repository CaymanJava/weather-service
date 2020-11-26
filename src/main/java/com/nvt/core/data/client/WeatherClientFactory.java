package com.nvt.core.data.client;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;

public class WeatherClientFactory {

    public static WeatherClient getClient() {
        return MockClientHolder.INSTANCE;
    }

    private static class MockClientHolder {

        final static WeatherMockClient INSTANCE = initMockClient();

        private static WeatherMockClient initMockClient() {
            return Feign.builder()
                    .client(new OkHttpClient())
                    .encoder(new GsonEncoder())
                    .decoder(new GsonDecoder())
                    .logLevel(Logger.Level.FULL)
                    .target(WeatherMockClient.class, "https://run.mocky.io/v3");
        }

    }

}
