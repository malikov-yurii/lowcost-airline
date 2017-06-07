package com.malikov.ticketsystem.web.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.malikov.ticketsystem.util.serializers.LocalDateTimeSerializer;

import java.time.LocalDateTime;

/**
 * @author Yurii Malikov
 */
public class JacksonObjectMapper extends ObjectMapper {

    private static final ObjectMapper MAPPER = new JacksonObjectMapper();

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    private JacksonObjectMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        registerModule(module);
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}