package ru.tchallenge.participant.service.domain.workbook;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.domain.assignment.Assignment;
import ru.tchallenge.participant.service.utility.data.IdAware;

import java.time.Instant;

@Data
@Builder
public final class Workbook implements IdAware {

    private final String id;
    private final String eventId;
    private final String ownerId;
    private final ImmutableList<Assignment> assignments;
    private final Instant submittableUntil;
    private final String status;
}
