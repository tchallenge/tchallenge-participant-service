package ru.tchallenge.pilot.service.domain.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import spark.Request;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.domain.problem.option.ProblemOptionDocument;
import ru.tchallenge.pilot.service.domain.problem.option.ProblemOptionInvoice;
import ru.tchallenge.pilot.service.domain.problem.snippet.ProblemSnippetDocument;
import ru.tchallenge.pilot.service.domain.problem.snippet.ProblemSnippetInvoice;
import ru.tchallenge.pilot.service.utility.data.IdAware;

@ManagedComponent
public class ProblemManager extends GenericApplicationComponent {

    private ProblemProjector problemProjector;
    private ProblemRepository problemRepository;

    @Override
    public void init() {
        super.init();
        this.problemProjector = getComponent(ProblemProjector.class);
        this.problemRepository = getComponent(ProblemRepository.class);
    }

    public IdAware create(Request request, ProblemInvoice invoice) {
        final ProblemDocument problemDocument = prepareNewProblem(invoice);
        problemRepository.insert(problemDocument);
        return problemProjector.problem(problemDocument, false);
    }

    public List<Problem> retrieveAll(Request request) {
        return problemRepository
                .findAll()
                .stream()
                .map(d -> problemProjector.problem(d, false))
                .collect(Collectors.toList());
    }

    public List<Problem> retrieveRandom(Request request, ProblemRandomInvoice invoice) {
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
