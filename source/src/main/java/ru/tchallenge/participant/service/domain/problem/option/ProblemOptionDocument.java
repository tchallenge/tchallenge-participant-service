package ru.tchallenge.participant.service.domain.problem.option;

import org.bson.Document;

import ru.tchallenge.participant.service.utility.data.DocumentWrapper;

public final class ProblemOptionDocument extends DocumentWrapper {

    private static final String ATTRIBUTE_CONTENT = "content";
    private static final String ATTRIBUTE_CORRECT = "correct";

    public ProblemOptionDocument(final Document document) {
        super(document);
    }

    public String getContent() {
        return retrieveString(ATTRIBUTE_CONTENT);
    }

    public Boolean getCorrect() {
        return retrieveBoolean(ATTRIBUTE_CORRECT);
    }
}
