package ru.tchallenge.pilot.service.domain.problem.option;

import org.bson.Document;

import ru.tchallenge.pilot.service.utility.data.DocumentWrapper;

public final class ProblemOptionDocument extends DocumentWrapper {

    private static final String ATTRIBUTE_CONTENT = "content";
    private static final String ATTRIBUTE_CORRECT = "correct";

    public ProblemOptionDocument() {

    }

    public ProblemOptionDocument(final Document document) {
        super(document);
    }

    public String getContent() {
        return retrieveString(ATTRIBUTE_CONTENT);
    }

    public ProblemOptionDocument content(final String content) {
        document.put(ATTRIBUTE_CONTENT, content);
        return this;
    }

    public Boolean getCorrect() {
        return retrieveBoolean(ATTRIBUTE_CORRECT);
    }

    public ProblemOptionDocument correct(final Boolean correct) {
        document.put(ATTRIBUTE_CORRECT, correct);
        return this;
    }
}
