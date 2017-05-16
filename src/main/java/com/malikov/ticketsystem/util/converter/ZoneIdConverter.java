package com.malikov.ticketsystem.util.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.ZoneId;

/**
 * @author Yurii Malikov
 */
@Converter(autoApply = true)
public class ZoneIdConverter implements AttributeConverter<ZoneId, String> {

    @Override
    public String convertToDatabaseColumn(ZoneId attribute) {
        return attribute.getId();
    }

    @Override
    public ZoneId convertToEntityAttribute(String dbData) {
        return ZoneId.of(dbData);
    }
}
