package ru.tchallenge.participant.service.domain.event;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.domain.maturity.Maturity;
import ru.tchallenge.participant.service.domain.specialization.Specialization;
import ru.tchallenge.participant.service.utility.data.Id;
import ru.tchallenge.participant.service.utility.data.IdAware;

import java.time.Instant;
import java.util.Collection;

@Data
@Builder
public final class Event implements IdAware {

    private final Id id;
    private final String textcode;
    private final String caption;
    private final String description;
    private final String greeting;
    private final Instant validFrom;
    private final Instant validUntil;
    private final Collection<String> notifications;
    private final Collection<Maturity> maturities;
    private final Collection<Specialization> specializations;
    private final String status;
}
