package ru.tchallenge.participant.service.domain.problem.snippet;

import org.bson.Document;

import ru.tchallenge.participant.service.utility.data.DocumentWrapper;

public final class ProblemSnippetDocument extends DocumentWrapper {

    private static final String ATTRIBUTE_CONTENT = "content";
    private static final String ATTRIBUTE_STYLE = "style";

    public ProblemSnippetDocument(final Document document) {
        super(document);
    }

    public String getContent() {
        return retrieveString(ATTRIBUTE_CONTENT);
    }

    public ProblemSnippetStyle getStyle() {
        return ProblemSnippetStyle.valueOf(retrieveString(ATTRIBUTE_STYLE));
    }
}
