package ru.tchallenge.participant.service.domain.specialization;

import com.google.common.collect.ImmutableList;
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
    private final ImmutableList<ProblemCategory> problemCategories;
}
