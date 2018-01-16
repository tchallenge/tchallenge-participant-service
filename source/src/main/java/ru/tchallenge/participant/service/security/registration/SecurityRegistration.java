package ru.tchallenge.participant.service.security.registration;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.utility.data.IdAware;

@Data
@Builder
public class SecurityRegistration implements IdAware {

    private final String id;
}
