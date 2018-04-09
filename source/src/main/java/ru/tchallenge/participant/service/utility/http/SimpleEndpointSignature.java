package ru.tchallenge.participant.service.utility.http;

import java.util.Objects;

import lombok.Builder;
import lombok.Data;

import spark.Request;

@Data
@Builder
public final class SimpleEndpointSignature {

    private final String method;
    private final String uri;

    public boolean matches(final Request request) {
        Objects.requireNonNull(request, "Request cannot be null");
        if (this.method != null && !request.requestMethod().equalsIgnoreCase(this.method)) {
            return false;
        }
        if (this.uri != null && !request.uri().matches(this.uri)) {
            return false;
        }
        return true;
    }
}
