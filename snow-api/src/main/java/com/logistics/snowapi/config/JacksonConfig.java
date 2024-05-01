package com.logistics.snowapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.logistics.snowapi.MultiPolygonDeserializer;
import com.logistics.snowapi.MultiPolygonSerializer;
import org.locationtech.jts.geom.MultiPolygon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Registering JavaTimeModule
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Optional: to use ISO-8601 format
        SimpleModule module = new SimpleModule();
        module.addSerializer(MultiPolygon.class, new MultiPolygonSerializer());
        module.addDeserializer(MultiPolygon.class, new MultiPolygonDeserializer());
        mapper.registerModule(module);
        return mapper;
    }
}