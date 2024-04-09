package com.legion.mediaservice.utils.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class LocalDateSetDeserializer extends JsonDeserializer<Set<LocalDate>> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public Set<LocalDate> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Set<LocalDate> localDates = new HashSet<>();
        String[] dateStrings = jsonParser.readValueAs(String[].class);

        if (dateStrings != null) {
            for (String dateString : dateStrings) {
                LocalDate date = LocalDate.parse(dateString, FORMATTER);
                localDates.add(date);
            }
        }

        return localDates;
    }
}
