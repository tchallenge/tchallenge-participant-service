package ru.tchallenge.participant.service.domain.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.tchallenge.participant.service.domain.problem.option.ProblemOptionDocument;
import ru.tchallenge.participant.service.domain.problem.option.ProblemOptionInvoice;
import ru.tchallenge.participant.service.domain.problem.snippet.ProblemSnippetDocument;
import ru.tchallenge.participant.service.domain.problem.snippet.ProblemSnippetInvoice;
import ru.tchallenge.participant.service.utility.data.IdAware;

public final class ProblemManager {

    public static final ProblemManager INSTANCE = new ProblemManager();

    private final ProblemProjector problemProjector = ProblemProjector.INSTANCE;
    private final ProblemRepository problemRepository = ProblemRepository.INSTANCE;

    private ProblemManager() {

    }

    public IdAware create(final ProblemInvoice invoice) {
        final ProblemDocument problemDocument = prepareNewProblem(invoice);
        problemRepository.insert(problemDocument);
        return problemProjector.problem(problemDocument, false);
    }

    public List<Problem> retrieveAll() {
        return problemRepository
                .findAll()
                .stream()
                .map(d -> problemProjector.problem(d, false))
                .collect(Collectors.toList());
    }

    public List<Problem> retrieveRandom(final ProblemRandomInvoice invoice) {
        return problemRepository
                .findRandom(invoice)
                .stream()
                .map(d -> problemProjector.problem(d, false))
                .collect(Collectors.toList());
    }

    private ProblemDocument prepareNewProblem(final ProblemInvoice invoice) {
        return new ProblemDocument()
                .categories(invoice.getCategories())
                .complexity(invoice.getComplexity())
                .difficulty(invoice.getDifficulty())
                .expectation(invoice.getExpectation())
                .introduction(invoice.getIntroduction())
                .options(prepareNewOptions(invoice))
                .question(invoice.getQuestion())
                .snippets(prepareNewSnippets(invoice))
                .status(ProblemStatus.APPROVED);
    }

    private List<ProblemOptionDocument> prepareNewOptions(final ProblemInvoice invoice) {
        return invoice.getOptions()
                .stream()
                .map(this::prepareNewOption)
                .collect(Collectors.toList());
    }

    private ProblemOptionDocument prepareNewOption(final ProblemOptionInvoice invoice) {
        return new ProblemOptionDocument()
                .content(invoice.getContent())
                .correct(invoice.getCorrect());
    }

    private List<ProblemSnippetDocument> prepareNewSnippets(final ProblemInvoice invoice) {
        if (invoice.getSnippets() == null) {
            return new ArrayList<>();
        }
        return invoice.getSnippets()
                .stream()
                .map(this::prepareNewSnippet)
                .collect(Collectors.toList());
    }

    private ProblemSnippetDocument prepareNewSnippet(final ProblemSnippetInvoice invoice) {
        return new ProblemSnippetDocument()
                .content(invoice.getContent())
                .style(invoice.getStyle());
    }
}
