package ru.tchallenge.pilot.service.domain.event;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import ru.tchallenge.pilot.service.domain.maturity.Maturity;
import ru.tchallenge.pilot.service.utility.data.DocumentWrapper;
import ru.tchallenge.pilot.service.utility.data.Id;

public final class EventDocument extends DocumentWrapper {

    public static Bson filter(final EventFilter eventFilter) {
        final Bson byPermalink = Filters.regex(ATTRIBUTE_PERMALINK, eventFilter.getPermalink());
        final Bson byStatus = Filters.in(ATTRIBUTE_STATUS, setOfStrings(eventFilter.getStatuses()));
        return Filters.and(byPermalink, byStatus);
    }

    private static final String ATTRIBUTE_CAPTION = "caption";
    private static final String ATTRIBUTE_DESCRIPTION = "description";
    private static final String ATTRIBUTE_GREETING = "greeting";
    private static final String ATTRIBUTE_MATURITIES = "maturities";
    private static final String ATTRIBUTE_NOTIFICATIONS = "notifications";
    private static final String ATTRIBUTE_PERMALINK = "permalink";
    private static final String ATTRIBUTE_SPECIALIZATION_IDS = "specializationIds";
    private static final String ATTRIBUTE_STATUS = "status";
    private static final String ATTRIBUTE_VALID_FROM = "validFrom";
    private static final String ATTRIBUTE_VALID_UNTIL = "validUntil";

    public EventDocument(final Document document) {
        super(document);
    }

    public String getCaption() {
        return retrieveString(ATTRIBUTE_CAPTION);
    }

    public String getDescription() {
        return retrieveString(ATTRIBUTE_DESCRIPTION);
    }

    public String getGreeting() {
        return retrieveString(ATTRIBUTE_GREETING);
    }

    public List<Maturity> getMaturities() {
        return retrieveListOfStrings(ATTRIBUTE_MATURITIES)
                .stream()
                .map(Maturity::valueOf)
                .collect(Collectors.toList());
    }

    public List<String> getNotifications() {
        return retrieveListOfStrings(ATTRIBUTE_NOTIFICATIONS);
    }

    public String getPermalink() {
        return retrieveString(ATTRIBUTE_PERMALINK);
    }

    public List<Id> getSpecializationIds() {
        return retrieveListOfIds(ATTRIBUTE_SPECIALIZATION_IDS);
    }

    public Instant getValidFrom() {
        return retrieveInstant(ATTRIBUTE_VALID_FROM);
    }

    public Instant getValidUntil() {
        return retrieveInstant(ATTRIBUTE_VALID_UNTIL);
    }

    public EventStatus getStatus() {
        return EventStatus.valueOf(retrieveString(ATTRIBUTE_STATUS));
    }
}
