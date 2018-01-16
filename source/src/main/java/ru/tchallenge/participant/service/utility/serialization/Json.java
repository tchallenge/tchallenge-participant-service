package ru.tchallenge.participant.service.utility.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.tchallenge.participant.service.utility.validation.ValidationAware;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.Collections;

public final class Json {

    public static String json(final Response response) {
        return json (null, response);
    }

    public static String json(final Object data, final Response response) {
        response.header("Content-Type", "application/json");
        try {
            return OBJECT_MAPPER.writeValueAsString(data != null ? data : EMPTY);
        } catch (final IOException exception) {
            throw new RuntimeException("JSON serialization has failed", exception);
        }
    }

    public static <T extends ValidationAware> T body(final Class<T> type, final Request request) {
        final String requestBody = request.body();
        if (requestBody == null || requestBody.isEmpty()) {
            throw new RuntimeException("Request body is missing");
        }
        final T result;
        try {
            result = OBJECT_MAPPER.readValue(request.body(), type);
        } catch (final IOException exception) {
            throw new RuntimeException("Request body format is invalid", exception);
        }
        result.validate();
        return result;
    }

    private static final Object EMPTY = Collections.EMPTY_MAP;
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        // TODO: configure the object mapper
    }

    private Json() {

    }
}
