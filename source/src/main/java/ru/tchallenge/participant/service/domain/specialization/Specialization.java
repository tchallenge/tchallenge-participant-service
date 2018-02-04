package ru.tchallenge.participant.service.domain.specialization;

import java.util.List;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.participant.service.domain.problem.ProblemCategory;
import ru.tchallenge.participant.service.utility.data.Id;

@Data
@Builder
public final class Specialization {

    private final Id id;
    private final String caption;
    private final String permalink;
    private final List<ProblemCategory> problemCategories;
}
