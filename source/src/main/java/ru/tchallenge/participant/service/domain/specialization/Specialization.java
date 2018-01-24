package ru.tchallenge.participant.service.domain.specialization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Specialization {

    private final String caption;
    private final String textcode;
}
