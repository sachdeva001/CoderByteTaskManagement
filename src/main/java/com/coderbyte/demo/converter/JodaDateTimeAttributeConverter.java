package com.coderbyte.demo.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.joda.time.DateTime;

import java.sql.Timestamp;

@Converter(autoApply = false)
public class JodaDateTimeAttributeConverter implements AttributeConverter<DateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(DateTime attribute) {
        if (attribute == null) return null;
        return new Timestamp(attribute.getMillis());
    }

    @Override
    public DateTime convertToEntityAttribute(Timestamp dbData) {
        if (dbData == null) return null;
        return new DateTime(dbData.getTime());
    }
}
