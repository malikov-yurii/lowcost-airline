package com.malikov.ticketsystem.util.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.malikov.ticketsystem.util.DateTimeUtil.DATE_TIME_FORMATTER;

/**
 * @author Yurii Malikov
 */
// TODO: 5/23/2017 NEED that because @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) on TO NOT working fine??
public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
//public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private static final long serialVersionUID = 1L;

    public LocalDateTimeSerializer(){
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider sp) throws IOException, JsonProcessingException {
        gen.writeString(value.format(DATE_TIME_FORMATTER));
    }
}