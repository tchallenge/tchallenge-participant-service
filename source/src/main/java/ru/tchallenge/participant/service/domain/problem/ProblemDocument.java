package ru.tchallenge.participant.service.domain.problem;

import org.bson.Document;
import ru.tchallenge.participant.service.utility.data.DocumentWrapper;

import java.util.List;
import java.util.stream.Collectors;

public final class ProblemDocument extends DocumentWrapper {

    private static final String ATTRIBUTE_CATEGORIES = "categories";
    private static final String ATTRIBUTE_DIFFICULTY = "difficulty";
    private static final String ATTRIBUTE_EXPECTATION = "expectation";
    private static final String ATTRIBUTE_INTRODUCTION = "introduction";
    private static final String ATTRIBUTE_QUESTION = "question";

    public ProblemDocument(final Document document) {
        super(document);
    }

    public List<ProblemCategory> getCategories() {
        return retrieveListOfStrings(ATTRIBUTE_CATEGORIES)
                .stream()
                .map(ProblemCategory::valueOf)
                .collect(Collectors.toList());
    }

    public ProblemDifficulty getDifficulty() {
        return ProblemDifficulty.valueOf(document.getString(ATTRIBUTE_DIFFICULTY));
    }

    public ProblemExpectation getExpectation() {
        return ProblemExpectation.valueOf(document.getString(ATTRIBUTE_EXPECTATION));
    }

    public String getIntroduction() {
        return document.getString(ATTRIBUTE_INTRODUCTION);
    }

    public String getQuestion() {
        return document.getString(ATTRIBUTE_QUESTION);
    }

    @SuppressWarnings("unchecked")
    private List<String> retrieveListOfStrings(final String attribute) {
        return (List<String>) document.get(attribute);
    }
}
