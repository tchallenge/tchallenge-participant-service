package ru.tchallenge.participant.service.domain.problem.snippet;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.participant.service.utility.validation.ValidationAware;

@Data
@Builder
public final class ProblemSnippetInvoice implements ValidationAware {

    private final String content;
    private final ProblemSnippetStyle style;

    @Override
    public void registerViolations(final Collection<String> violations) {

    }
}
