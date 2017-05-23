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

// TODO: 5/23/2017 Don't need that because @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) on TO works fine??
public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
//public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final long serialVersionUID = 1L;

    public LocalDateTimeDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return DateTimeUtil.parseToLocalDateTime(jp.readValueAs(String.class));
    }

}