package ru.tchallenge.pilot.service.domain.event;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class EventFilter {

    private Boolean admittedOnly;
    private Set<EventStatus> statuses;
    private String permalink;
}
