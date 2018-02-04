package ru.tchallenge.participant.service.domain.event;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.participant.service.domain.maturity.Maturity;
import ru.tchallenge.participant.service.domain.specialization.Specialization;
import ru.tchallenge.participant.service.utility.data.Id;
import ru.tchallenge.participant.service.utility.data.IdAware;

@Data
@Builder
public final class Event implements IdAware {

    private final Id id;
    private final String permalink;
    private final String caption;
    private final String description;
    private final String greeting;
    private final Instant validFrom;
    private final Instant validUntil;
    private final List<String> notifications;
    private final List<Maturity> maturities;
    private final List<Id> specializationIds;
    private final EventStatus status;
}
