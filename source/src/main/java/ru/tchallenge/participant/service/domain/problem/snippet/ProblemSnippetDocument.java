package ru.tchallenge.participant.service.domain.problem.snippet;

import org.bson.Document;

import ru.tchallenge.participant.service.utility.data.DocumentWrapper;

public final class ProblemSnippetDocument extends DocumentWrapper {

    private static final String ATTRIBUTE_CONTENT = "content";
    private static final String ATTRIBUTE_STYLE = "style";

    public ProblemSnippetDocument() {

    }

    public ProblemSnippetDocument(final Document document) {
        super(document);
    }

    public String getContent() {
        return retrieveString(ATTRIBUTE_CONTENT);
    }

    public ProblemSnippetDocument content(final String content) {
        document.put(ATTRIBUTE_CONTENT, content);
        return this;
    }

    public ProblemSnippetStyle getStyle() {
        return ProblemSnippetStyle.valueOf(retrieveString(ATTRIBUTE_STYLE));
    }

    public ProblemSnippetDocument style(final ProblemSnippetStyle style) {
        document.put(ATTRIBUTE_STYLE, style.name());
        return this;
    }
}
