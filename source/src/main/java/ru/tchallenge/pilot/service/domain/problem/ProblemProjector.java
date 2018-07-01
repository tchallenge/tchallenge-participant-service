package ru.tchallenge.pilot.service.domain.problem;

import java.util.List;
import java.util.stream.Collectors;

import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.domain.problem.image.ProblemImage;
import ru.tchallenge.pilot.service.domain.problem.image.ProblemImageDocument;
import ru.tchallenge.pilot.service.domain.problem.image.ProblemImageProjector;
import ru.tchallenge.pilot.service.domain.problem.option.ProblemOption;
import ru.tchallenge.pilot.service.domain.problem.option.ProblemOptionDocument;
import ru.tchallenge.pilot.service.domain.problem.option.ProblemOptionProjector;
import ru.tchallenge.pilot.service.domain.problem.snippet.ProblemSnippet;
import ru.tchallenge.pilot.service.domain.problem.snippet.ProblemSnippetDocument;
import ru.tchallenge.pilot.service.domain.problem.snippet.ProblemSnippetProjector;
import ru.tchallenge.pilot.service.utility.data.GenericProjector;

@ManagedComponent
public class ProblemProjector extends GenericProjector {

    private ProblemImageProjector imageProjector;
    private ProblemOptionProjector optionProjector;
    private ProblemSnippetProjector snippetProjector;

    @Override
    public void init() {
        super.init();
        this.imageProjector = getComponent(ProblemImageProjector.class);
        this.optionProjector = getComponent(ProblemOptionProjector.class);
        this.snippetProjector = getComponent(ProblemSnippetProjector.class);
    }

    public Problem problem(final ProblemDocument document, final boolean classified) {
        return Problem.builder()
                .complexity(0)
                .categories(immutableList(document.getCategories()))
                .complexity(document.getComplexity())
                .difficulty(document.getDifficulty())
                .expectation(document.getExpectation())
                .images(immutableList(problemImages(document.getImages())))
                .introduction(document.getIntroduction())
                .options(immutableList(problemOptions(document.getOptions(), classified)))
                .question(document.getQuestion())
                .snippets(immutableList(problemSnippets(document.getSnippets())))
                .build();
    }

    private List<ProblemImage> problemImages(final List<ProblemImageDocument> documents) {
        return documents
                .stream()
                .map(imageProjector::problemImage)
                .collect(Collectors.toList());
    }

    private List<ProblemOption> problemOptions(final List<ProblemOptionDocument> documents, final boolean classified) {
        final Indexer indexer = new Indexer();
        return documents
                .stream()
                .map(d -> optionProjector.problemOption(d, classified, indexer.inc()))
                .collect(Collectors.toList());
    }

    private List<ProblemSnippet> problemSnippets(final List<ProblemSnippetDocument> documents) {
        return documents
                .stream()
                .map(snippetProjector::problemSnippet)
                .collect(Collectors.toList());
    }

    private static final class Indexer {

        private int index = 0;

        public int inc() {
            return ++index;
        }
    }
}
