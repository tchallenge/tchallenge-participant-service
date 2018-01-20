package ru.tchallenge.participant.service.domain.event;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public final class EventFilter {

    private Boolean admittedOnly;
    private Set<String> statuses;
    private String textcode;
}
