package ru.tchallenge.participant.service.domain.event;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.utility.data.IdAware;

import java.time.Instant;
import java.util.Collection;

@Data
@Builder
public final class Event implements IdAware {

    private final String id;
    private final String textcode;
    private final String caption;
    private final String description;
    private final String greeting;
    private final Instant validFrom;
    private final Instant validUntil;
    private final Collection<String> notifications;
    private final Collection<String> maturities;
    private final Collection<String> specializations;
    private final String status;
}
