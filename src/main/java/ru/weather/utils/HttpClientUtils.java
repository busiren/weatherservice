package ru.weather.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class HttpClientUtils {

    public static RestTemplate createRestTemplate(Integer maxConnections, Integer connectTimeout) {
        CloseableHttpClient client =
                createInternal(maxConnections, connectTimeout);
        return createRestTemplate(client);
    }

    private static CloseableHttpClient createInternal(Integer maxConnections, Integer connectTimeout) {
        HttpClientBuilder customClient = HttpClients.custom();
        if (maxConnections != null) {
            customClient.setMaxConnPerRoute(maxConnections);
            customClient.setMaxConnTotal(maxConnections * 2);
        }

        if (connectTimeout != null) {
            RequestConfig config = RequestConfig.custom()
                    .setConnectionRequestTimeout(connectTimeout)
                    .setConnectTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout)
                    .build();
            customClient.setDefaultRequestConfig(config);
        }

        return customClient.build();
    }

    public static RestTemplate createRestTemplate(CloseableHttpClient client) {
        ClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);
        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate;
    }
}
