package com.f4.fqs.eventStore.model;

import com.f4.fqs.commons.domain.message.EventType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class StringToEventTypeConverter implements Converter<String, EventType> {
    @Override
    public EventType convert(String source) {
        return EventType.valueOf(source);
    }
}
