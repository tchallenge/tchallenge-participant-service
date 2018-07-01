package ru.tchallenge.pilot.service.domain.problem;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Set;

import ru.tchallenge.pilot.service.utility.validation.ValidationAware;

@Data
@Builder
public final class ProblemRandomInvoice implements ValidationAware {

    private Set<ProblemCategory> categories;
    private ProblemDifficulty difficulty;
    private Integer number;

    @Override
    public void registerViolations(final Collection<String> violations) {

    }
}
