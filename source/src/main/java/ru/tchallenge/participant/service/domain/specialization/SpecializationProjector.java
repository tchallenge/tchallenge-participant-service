package ru.tchallenge.participant.service.domain.specialization;

import ru.tchallenge.participant.service.utility.data.GenericProjector;

public final class SpecializationProjector extends GenericProjector {

    public static final SpecializationProjector INSTANCE = new SpecializationProjector();

    private SpecializationProjector() {

    }

    public Specialization specialization(final SpecializationDocument document) {
        return Specialization.builder()
                .id(document.getId())
                .caption(document.getCaption())
                .permalink(document.getPermalink())
                .problemCategories(immutableList(document.getProblemCategories()))
                .build();
    }
}
