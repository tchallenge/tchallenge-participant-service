package ru.tchallenge.pilot.service.domain.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class EventSearchInvoice {

    private EventFilter filter;
    private Integer limit;
    private Integer offset;
}
