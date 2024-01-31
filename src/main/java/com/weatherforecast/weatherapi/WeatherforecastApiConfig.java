package com.weatherforecast.weatherapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;


@Configuration
public class WeatherforecastApiConfig {

    @Autowired
    private Environment environment;

    @Bean
    public WebClient webClient() throws Exception {
        try{
            int maxConnection = Integer.parseInt(environment.getProperty("weather-forecast-api-max-connection"));
            int maxPendingRequests = Integer.parseInt(environment.getProperty("weather-forecast-api-max-pending-requests"));
            ConnectionProvider provider = ConnectionProvider.builder("builder").maxConnections(maxConnection).pendingAcquireMaxCount(maxPendingRequests).build();
            HttpClient httpClient = HttpClient.create(provider);
            String url = environment.getProperty("weather-forecast-base-url");;
            if (url != null) {
                return WebClient.builder().baseUrl(url)
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();
            }
            return WebClient.builder().build();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
