package ru.tchallenge.pilot.service.domain.account;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

import ru.tchallenge.pilot.service.utility.data.Id;
import ru.tchallenge.pilot.service.utility.data.IdAware;

@Data
@Builder
public final class Account implements IdAware {

    private final Id id;
    private final String email;
    private final String passwordHash;
    private final AccountPersonality personality;
    private final String status;
    private final Instant registeredAt;
}
