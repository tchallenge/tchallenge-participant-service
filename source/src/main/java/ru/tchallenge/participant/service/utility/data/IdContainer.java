package ru.tchallenge.participant.service.utility.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class IdContainer implements IdAware {

    private final Id id;
}
