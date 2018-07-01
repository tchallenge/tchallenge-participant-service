package ru.tchallenge.pilot.service.domain.specialization;

import java.util.List;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.pilot.service.domain.problem.ProblemCategory;
import ru.tchallenge.pilot.service.utility.data.Id;

@Data
@Builder
public final class Specialization {

    private final Id id;
    private final String caption;
    private final String permalink;
    private final List<ProblemCategory> problemCategories;
}
