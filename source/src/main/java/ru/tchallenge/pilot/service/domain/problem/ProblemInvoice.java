package ru.tchallenge.pilot.service.domain.problem;

import java.util.Collection;
import java.util.List;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.pilot.service.domain.problem.option.ProblemOptionInvoice;
import ru.tchallenge.pilot.service.domain.problem.snippet.ProblemSnippetInvoice;
import ru.tchallenge.pilot.service.utility.validation.ValidationAware;

@Data
@Builder
public final class ProblemInvoice implements ValidationAware {

    private final List<ProblemCategory> categories;
    private final Integer complexity;
    private final ProblemDifficulty difficulty;
    private final ProblemExpectation expectation;
    private final String introduction;
    private final List<ProblemOptionInvoice> options;
    private final String question;
    private final List<ProblemSnippetInvoice> snippets;

    @Override
    public void registerViolations(final Collection<String> violations) {

    }
}
