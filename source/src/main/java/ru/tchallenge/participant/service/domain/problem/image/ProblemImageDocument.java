package ru.tchallenge.participant.service.domain.problem.image;

import org.bson.Document;

import ru.tchallenge.participant.service.utility.data.DocumentWrapper;
import ru.tchallenge.participant.service.utility.data.Id;

public final class ProblemImageDocument extends DocumentWrapper {

    private static final String ATTRIBUTE_BINARY_ID = "binaryId";
    private static final String ATTRIBUTE_FORMAT = "format";
    private static final String ATTRIBUTE_HEIGHT = "height";
    private static final String ATTRIBUTE_WIDTH = "width";

    public ProblemImageDocument(final Document document) {
        super(document);
    }

    public Id getBinaryId() {
        return retrieveId(ATTRIBUTE_BINARY_ID);
    }

    public ProblemImageFormat getFormat() {
        return ProblemImageFormat.valueOf(retrieveString(ATTRIBUTE_FORMAT));
    }

    public Integer getHeight() {
        return retrieveInteger(ATTRIBUTE_HEIGHT);
    }

    public Integer getWidth() {
        return retrieveInteger(ATTRIBUTE_WIDTH);
    }
}
