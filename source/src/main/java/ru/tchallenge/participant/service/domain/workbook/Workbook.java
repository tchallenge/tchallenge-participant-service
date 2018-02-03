package ru.tchallenge.participant.service.domain.workbook;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.domain.maturity.Maturity;
import ru.tchallenge.participant.service.domain.workbook.assignment.Assignment;
import ru.tchallenge.participant.service.utility.data.Id;
import ru.tchallenge.participant.service.utility.data.IdAware;

import java.time.Instant;

@Data
@Builder
public final class Workbook implements IdAware {

    private final Id id;
    private final Id eventId;
    private final Id specializationId;
    private final Id ownerId;
    private Maturity maturity;
    private final ImmutableList<Assignment> assignments;
    private final Instant submittableUntil;
    private final WorkbookStatus status;
}
