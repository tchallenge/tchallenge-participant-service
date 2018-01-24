package ru.tchallenge.participant.service.domain.maturity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Maturity {

    private final String caption;
    private final String textcode;
}
