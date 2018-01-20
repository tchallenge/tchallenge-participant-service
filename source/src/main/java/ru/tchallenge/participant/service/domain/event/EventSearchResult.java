package ru.tchallenge.participant.service.domain.event;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class EventSearchResult {

    private final ImmutableList<Event> items;
    private final Integer total;
}
