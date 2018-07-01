package ru.tchallenge.pilot.service.domain.workbook;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

import ru.tchallenge.pilot.service.domain.maturity.Maturity;
import ru.tchallenge.pilot.service.domain.workbook.assignment.AssignmentDocument;
import ru.tchallenge.pilot.service.utility.data.DocumentWrapper;
import ru.tchallenge.pilot.service.utility.data.Id;

public final class WorkbookDocument extends DocumentWrapper {

    private static final String ATTRIBUTE_ASSIGNMENTS = "assignments";
    private static final String ATTRIBUTE_EVENT_ID = "eventId";
    private static final String ATTRIBUTE_SPECIALIZATION_ID = "specializationId";
    private static final String ATTRIBUTE_OWNER_ID = "ownerId";
    private static final String ATTRIBUTE_MATURITY = "maturity";
    private static final String ATTRIBUTE_SUBMITTABLE_UNTIL = "submittableUntil";
    private static final String ATTRIBUTE_STATUS = "status";

    public WorkbookDocument() {

    }

    public WorkbookDocument(final Document document) {
        super(document);
    }

    public List<AssignmentDocument> getAssignments() {
        return retrieveListOfDocuments(ATTRIBUTE_ASSIGNMENTS, AssignmentDocument::new);
    }

    public WorkbookDocument assignments(final List<AssignmentDocument> assignments) {
        final List<Document> list = assignments
                .stream()
                .map(AssignmentDocument::getDocument)
                .collect(Collectors.toList());
        document.put(ATTRIBUTE_ASSIGNMENTS, list);
        return this;
    }

    public Id getEventId() {
        return retrieveId(ATTRIBUTE_EVENT_ID);
    }

    public WorkbookDocument eventId(final Id id) {
        document.put(ATTRIBUTE_EVENT_ID, id.toObjectId());
        return this;
    }

    public Id getSpecializationId() {
        return retrieveId(ATTRIBUTE_SPECIALIZATION_ID);
    }

    public WorkbookDocument specializationId(final Id id) {
        document.put(ATTRIBUTE_SPECIALIZATION_ID, id.toObjectId());
        return this;
    }

    public Id getOwnerId() {
        return retrieveId(ATTRIBUTE_OWNER_ID);
    }

    public WorkbookDocument ownerId(final Id id) {
        document.put(ATTRIBUTE_OWNER_ID, id.toObjectId());
        return this;
    }

    public Maturity getMaturity() {
        return Maturity.valueOf(retrieveString(ATTRIBUTE_MATURITY));
    }

    public WorkbookDocument maturity(final Maturity maturity) {
        document.put(ATTRIBUTE_MATURITY, maturity.name());
        return this;
    }

    public Instant getSubmittableUntil() {
        return retrieveInstant(ATTRIBUTE_SUBMITTABLE_UNTIL);
    }

    public WorkbookDocument submittableUntil(final Instant submittableUntil) {
        document.put(ATTRIBUTE_SUBMITTABLE_UNTIL, Date.from(submittableUntil));
        return this;
    }

    public WorkbookStatus getStatus() {
        return WorkbookStatus.valueOf(retrieveString(ATTRIBUTE_STATUS));
    }

    public WorkbookDocument status(final WorkbookStatus status) {
        document.put(ATTRIBUTE_STATUS, status.name());
        return this;
    }
}
