package ru.weather.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

@Slf4j
@Component
@AllArgsConstructor
public class MarshalUtils {

    private ObjectMapper mapper;

    public <T> T fromJson(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException var3) {
            log.error("Unable to read json: {}", json);
            throw new RuntimeException(var3);
        }
    }

    public <T> T fromJson(InputStream json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException var3) {
            log.error("Unable to read json from stream");
            throw new RuntimeException(var3);
        }
    }
}
