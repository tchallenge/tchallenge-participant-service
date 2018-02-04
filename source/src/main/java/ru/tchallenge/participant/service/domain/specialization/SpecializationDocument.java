package ru.tchallenge.participant.service.domain.specialization;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

import ru.tchallenge.participant.service.domain.problem.ProblemCategory;
import ru.tchallenge.participant.service.utility.data.DocumentWrapper;

public final class SpecializationDocument extends DocumentWrapper {

    private static final String ATTRIBUTE_CAPTION = "caption";
    private static final String ATTRIBUTE_PERMALINK = "permalink";
    private static final String ATTRIBUTE_PROBLEM_CATEGORIES = "problemCategories";

    public SpecializationDocument(final Document document) {
        super(document);
    }

    public String getCaption() {
        return retrieveString(ATTRIBUTE_CAPTION);
    }

    public String getPermalink() {
        return retrieveString(ATTRIBUTE_PERMALINK);
    }

    public List<ProblemCategory> getTaskCategories() {
        return retrieveListOfStrings(ATTRIBUTE_PROBLEM_CATEGORIES)
                .stream()
                .map(ProblemCategory::valueOf)
                .collect(Collectors.toList());
    }
}
