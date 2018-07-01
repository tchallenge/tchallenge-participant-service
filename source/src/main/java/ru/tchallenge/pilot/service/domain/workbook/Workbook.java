package ru.tchallenge.pilot.service.domain.workbook;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.pilot.service.domain.maturity.Maturity;
import ru.tchallenge.pilot.service.domain.workbook.assignment.Assignment;
import ru.tchallenge.pilot.service.utility.data.Id;
import ru.tchallenge.pilot.service.utility.data.IdAware;

@Data
@Builder
public final class Workbook implements IdAware {

    private final Id id;
    private final String textcode;
    private final List<Assignment> assignments;
    private final Id eventId;
    private final Id specializationId;
    private final Id ownerId;
    private Maturity maturity;
    private final Instant submittableUntil;
    private final WorkbookStatus status;
}
