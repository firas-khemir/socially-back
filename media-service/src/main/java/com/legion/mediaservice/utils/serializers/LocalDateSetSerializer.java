package com.legion.mediaservice.utils.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class LocalDateSetSerializer extends JsonSerializer<Set<LocalDate>> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void serialize(Set<LocalDate> localDates, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();

        for (LocalDate date : localDates) {
            jsonGenerator.writeString(date.format(FORMATTER));
        }

        jsonGenerator.writeEndArray();
    }
}
