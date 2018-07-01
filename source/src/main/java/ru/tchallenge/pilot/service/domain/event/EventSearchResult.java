package ru.tchallenge.pilot.service.domain.event;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class EventSearchResult {

    private final List<Event> items;
    private final Integer total;
}
