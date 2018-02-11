package ru.tchallenge.participant.service.domain.problem.option;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.participant.service.utility.validation.ValidationAware;

@Data
@Builder
public final class ProblemOptionInvoice implements ValidationAware {

    private final String content;
    private final Boolean correct;

    @Override
    public void registerViolations(final Collection<String> violations) {

    }
}
