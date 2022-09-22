package com.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class JSONUtils {
    private static ObjectMapper myObjectMapper = null;

    static {
        myObjectMapper = new ObjectMapper();
        myObjectMapper
                //              .configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    public static <T> Optional<T> fromJSON(String json, Class<T> clazz) {
        try {
            return Optional.ofNullable(myObjectMapper.readValue(json, clazz));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static String toJSON(Object o) {
        String json = "";
        try {
            json = myObjectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("toJSON", e);
        }

        return json;
    }

}
