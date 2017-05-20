package com.malikov.ticketsystem.util.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.malikov.ticketsystem.util.DateTimeUtil;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Yurii Malikov
 */

public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
//public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final long serialVersionUID = 1L;

    protected LocalDateTimeDeserializer() {
        super(LocalDateTime.class);
    }


    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return DateTimeUtil.parseLocalDateTime(jp.readValueAs(String.class));
    }

}