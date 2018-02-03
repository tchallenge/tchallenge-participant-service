package ru.tchallenge.participant.service.domain.workbook;

import org.bson.Document;
import org.bson.types.ObjectId;
import ru.tchallenge.participant.service.domain.maturity.Maturity;
import ru.tchallenge.participant.service.utility.persistence.DocumentWrapper;

import java.time.Instant;
import java.util.Date;

public final class WorkbookDocument extends DocumentWrapper {

    private static final String ATTRIBUTE_ID = "_id";
    private static final String ATTRIBUTE_EVENT_ID = "eventId";
    private static final String ATTRIBUTE_SPECIALIZATION_ID = "specializationId";
    private static final String ATTRIBUTE_OWNER_ID = "ownerId";
    private static final String ATTRIBUTE_MATURITY = "maturity";
    private static final String ATTRIBUTE_SUBMITTABLE_UNTIL = "submittableUntil";
    private static final String ATTRIBUTE_STATUS = "status";
    private static final String ATTRIBUTE_ASSIGNMENTS = "assignments";

    public WorkbookDocument() {
        this(new Document());
    }

    public WorkbookDocument(final Document document) {
        super(document);
    }

    public WorkbookDocument eventId(final ObjectId eventId) {
        getDocument().put(ATTRIBUTE_EVENT_ID, eventId);
        return this;
    }

    public WorkbookDocument specializationId(final ObjectId specializationId) {
        getDocument().put(ATTRIBUTE_SPECIALIZATION_ID, specializationId);
        return this;
    }

    public WorkbookDocument ownerId(final ObjectId ownerId) {
        getDocument().put(ATTRIBUTE_OWNER_ID, ownerId);
        return this;
    }

    public WorkbookDocument submittableUntil(final Instant submittableUntil) {
        getDocument().put(ATTRIBUTE_SUBMITTABLE_UNTIL, Date.from(submittableUntil));
        return this;
    }

    public WorkbookDocument status(final WorkbookStatus status) {
        getDocument().put(ATTRIBUTE_STATUS, status.name());
        return this;
    }

    public WorkbookDocument maturity(final Maturity maturity) {
        getDocument().put(ATTRIBUTE_MATURITY, maturity.name());
        return this;
    }
}
