package com.weatherforecast.weatherapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WeatherforecastApiConfig {

    @Autowired
    private Environment environment;

    @Bean
    public WebClient webClient() {
        String url = environment.getProperty("weather-forecast-base-url");;
        if (url != null) {
            return WebClient.builder().baseUrl(url).build();
        }
        return WebClient.builder().build();
    }
}
