package ru.tchallenge.participant.service.domain.problem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import ru.tchallenge.participant.service.domain.problem.image.ProblemImageDocument;
import ru.tchallenge.participant.service.domain.problem.option.ProblemOptionDocument;
import ru.tchallenge.participant.service.domain.problem.snippet.ProblemSnippetDocument;
import ru.tchallenge.participant.service.utility.data.DocumentWrapper;
import ru.tchallenge.participant.service.utility.data.Id;

public final class ProblemDocument extends DocumentWrapper {

    public static Bson filterByIds(final List<Id> ids) {
        return Filters.in(ATTRIBUTE_ID, ids.stream().map(Id::toObjectId).collect(Collectors.toList()));
    }

    public static Bson randomFilter(final Set<ProblemCategory> categories, final ProblemDifficulty difficulty) {
        final Bson categoryFilter = Filters.in(ATTRIBUTE_CATEGORIES, setOfStrings(categories));
        final Bson baseFilter = new Document()
                .append(ATTRIBUTE_DIFFICULTY, difficulty.name())
                .append(ATTRIBUTE_STATUS, ProblemStatus.APPROVED.name());
        return Filters.and(baseFilter, categoryFilter);
    }

    private static final String ATTRIBUTE_CATEGORIES = "categories";
    private static final String ATTRIBUTE_COMPLEXITY = "complexity";
    private static final String ATTRIBUTE_DIFFICULTY = "difficulty";
    private static final String ATTRIBUTE_EXPECTATION = "expectation";
    private static final String ATTRIBUTE_IMAGES = "images";
    private static final String ATTRIBUTE_INTRODUCTION = "introduction";
    private static final String ATTRIBUTE_OPTIONS = "options";
    private static final String ATTRIBUTE_QUESTION = "question";
    private static final String ATTRIBUTE_SNIPPETS = "snippets";
    private static final String ATTRIBUTE_STATUS = "status";

    public ProblemDocument() {

    }

    public ProblemDocument(final Document document) {
        super(document);
    }

    public List<ProblemCategory> getCategories() {
        return retrieveListOfStrings(ATTRIBUTE_CATEGORIES)
                .stream()
                .map(ProblemCategory::valueOf)
                .collect(Collectors.toList());
    }

    public ProblemDocument categories(final List<ProblemCategory> categories) {
        document.put(ATTRIBUTE_CATEGORIES, categories.stream().map(ProblemCategory::name).collect(Collectors.toList()));
        return this;
    }

    public Integer getComplexity() {
        return retrieveInteger(ATTRIBUTE_COMPLEXITY);
    }

    public ProblemDocument complexity(final Integer complexity) {
        document.put(ATTRIBUTE_DIFFICULTY, complexity);
        return this;
    }

    public ProblemDifficulty getDifficulty() {
        return ProblemDifficulty.valueOf(retrieveString(ATTRIBUTE_DIFFICULTY));
    }

    public ProblemDocument difficulty(final ProblemDifficulty difficulty) {
        document.put(ATTRIBUTE_DIFFICULTY, difficulty.name());
        return this;
    }

    public ProblemExpectation getExpectation() {
        return ProblemExpectation.valueOf(retrieveString(ATTRIBUTE_EXPECTATION));
    }

    public ProblemDocument expectation(final ProblemExpectation expectation) {
        document.put(ATTRIBUTE_EXPECTATION, expectation.name());
        return this;
    }

    public List<ProblemImageDocument> getImages() {
        return retrieveListOfDocuments(ATTRIBUTE_IMAGES, ProblemImageDocument::new);
    }

    public String getIntroduction() {
        return retrieveString(ATTRIBUTE_INTRODUCTION);
    }

    public ProblemDocument introduction(final String introduction) {
        document.put(ATTRIBUTE_INTRODUCTION, introduction);
        return this;
    }

    public List<ProblemOptionDocument> getOptions() {
        return retrieveListOfDocuments(ATTRIBUTE_OPTIONS, ProblemOptionDocument::new);
    }

    public ProblemDocument options(final List<ProblemOptionDocument> optionDocuments) {
        final List<Document> list = optionDocuments
                .stream()
                .map(ProblemOptionDocument::getDocument)
                .collect(Collectors.toList());
        document.put(ATTRIBUTE_OPTIONS, list);
        return this;
    }

    public String getQuestion() {
        return retrieveString(ATTRIBUTE_QUESTION);
    }

    public ProblemDocument question(final String question) {
        document.put(ATTRIBUTE_QUESTION, question);
        return this;
    }

    public List<ProblemSnippetDocument> getSnippets() {
        return retrieveListOfDocuments(ATTRIBUTE_SNIPPETS, ProblemSnippetDocument::new);
    }

    public ProblemDocument snippets(final List<ProblemSnippetDocument> snippetDocuments) {
        final List<Document> list = snippetDocuments
                .stream()
                .map(ProblemSnippetDocument::getDocument)
                .collect(Collectors.toList());
        document.put(ATTRIBUTE_SNIPPETS, list);
        return this;
    }

    public ProblemStatus getStatus() {
        return ProblemStatus.valueOf(retrieveString(ATTRIBUTE_STATUS));
    }

    public ProblemDocument status(final ProblemStatus status) {
        document.put(ATTRIBUTE_STATUS, status.name());
        return this;
    }
}
