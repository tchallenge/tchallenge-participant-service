package ru.tchallenge.participant.service.domain.workbook;

import org.bson.Document;
import ru.tchallenge.participant.service.domain.maturity.Maturity;
import ru.tchallenge.participant.service.utility.data.DocumentWrapper;
import ru.tchallenge.participant.service.utility.data.Id;

import java.time.Instant;
import java.util.Date;

public final class WorkbookDocument extends DocumentWrapper {

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

    public WorkbookDocument eventId(final Id id) {
        document.put(ATTRIBUTE_EVENT_ID, id.toObjectId());
        return this;
    }

    public Id getEventId() {
        return new Id(document.getObjectId(ATTRIBUTE_EVENT_ID));
    }

    public WorkbookDocument specializationId(final Id id) {
        document.put(ATTRIBUTE_SPECIALIZATION_ID, id.toObjectId());
        return this;
    }

    public Id getSpecializationId() {
        return new Id(document.getObjectId(ATTRIBUTE_SPECIALIZATION_ID));
    }

    public WorkbookDocument ownerId(final Id id) {
        document.put(ATTRIBUTE_OWNER_ID, id.toObjectId());
        return this;
    }

    public Id getOwnerId() {
        return new Id(document.getObjectId(ATTRIBUTE_OWNER_ID));
    }

    public WorkbookDocument maturity(final Maturity maturity) {
        document.put(ATTRIBUTE_MATURITY, maturity.name());
        return this;
    }

    public Maturity getMaturity() {
        return Maturity.valueOf(document.getString(ATTRIBUTE_MATURITY));
    }

    public WorkbookDocument submittableUntil(final Instant submittableUntil) {
        document.put(ATTRIBUTE_SUBMITTABLE_UNTIL, Date.from(submittableUntil));
        return this;
    }

    public Instant getSubmittableUntil() {
        return document.getDate(ATTRIBUTE_SUBMITTABLE_UNTIL).toInstant();
    }

    public WorkbookDocument status(final WorkbookStatus status) {
        document.put(ATTRIBUTE_STATUS, status.name());
        return this;
    }

    public WorkbookStatus getStatus() {
        return WorkbookStatus.valueOf(document.getString(ATTRIBUTE_STATUS));
    }
}
