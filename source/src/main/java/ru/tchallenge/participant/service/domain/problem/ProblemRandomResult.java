package ru.tchallenge.participant.service.domain.problem;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProblemRandomResult {

    private final ImmutableList<Problem> items;
}
