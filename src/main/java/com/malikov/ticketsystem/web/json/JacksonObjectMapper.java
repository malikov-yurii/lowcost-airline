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
        //ObjectMapper mapper = new ObjectMapper();

        //registerModule(new Hibernate5Module());

        //registerModule(new JavaTimeModule());


        SimpleModule module = new SimpleModule();
        //module.addDeserializer(LocalDateTime.class, new OffsetDateTimeDeserializer()); // TODO: 5/23/2017 why i don't need deserializer but need serializer???????
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        //module.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
        registerModule(module);

        //registerModule(new JavaTimeModule());
        //findAndRegisterModules();


        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}