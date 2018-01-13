package ru.tchallenge.participant.service.utility.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import spark.Request;
import spark.ResponseTransformer;

import java.io.IOException;

public final class Json {

    public static ResponseTransformer asJson() {
        return RESPONSE_TRANSFORMER;
    }

    public static <T> T body(final Request request, final Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(request.body(), type);
        } catch (final IOException exception) {
            throw new RuntimeException("Request body cannot be parsed", exception);
        }
    }

    private static final ObjectMapper OBJECT_MAPPER;
    private static final ResponseTransformer RESPONSE_TRANSFORMER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        // TODO: configure the object mapper
        RESPONSE_TRANSFORMER = OBJECT_MAPPER::writeValueAsString;
    }

    private Json() {

    }
}
