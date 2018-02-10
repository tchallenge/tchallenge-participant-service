package ru.tchallenge.participant.service.utility.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.tchallenge.participant.service.utility.validation.ValidationAware;
import spark.Request;
import spark.Response;

import java.io.IOException;

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

    private static final Object EMPTY = new Object();
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.findAndRegisterModules();
    }

    private Json() {

    }
}
