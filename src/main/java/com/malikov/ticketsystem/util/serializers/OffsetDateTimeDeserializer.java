package com.malikov.ticketsystem.util.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.OffsetDateTime;

/**
 * @author Yurii Malikov
 */

// TODO: 5/23/2017 NOT WORKING!!
public class OffsetDateTimeDeserializer extends StdDeserializer<OffsetDateTime> {
//public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    public OffsetDateTimeDeserializer() {
        super(OffsetDateTime.class);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return OffsetDateTime.parse(jp.readValueAs(String.class));
    }

}