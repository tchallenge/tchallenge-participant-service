package ru.tchallenge.participant.service.domain.workbook;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.participant.service.domain.maturity.Maturity;
import ru.tchallenge.participant.service.domain.workbook.assignment.Assignment;
import ru.tchallenge.participant.service.utility.data.Id;
import ru.tchallenge.participant.service.utility.data.IdAware;

@Data
@Builder
public final class Workbook implements IdAware {

    private final Id id;
    private final List<Assignment> assignments;
    private final Id eventId;
    private final Id specializationId;
    private final Id ownerId;
    private Maturity maturity;
    private final Instant submittableUntil;
    private final WorkbookStatus status;
}
