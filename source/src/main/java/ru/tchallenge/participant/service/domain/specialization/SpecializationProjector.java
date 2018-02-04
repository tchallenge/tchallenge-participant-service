package ru.tchallenge.participant.service.domain.specialization;

import ru.tchallenge.participant.service.utility.data.GenericProjector;

public final class SpecializationProjector extends GenericProjector {

    public static final SpecializationProjector INSTANCE = new SpecializationProjector();

    private SpecializationProjector() {

    }

    public Specialization specialization(final SpecializationDocument document) {
        return Specialization.builder()
                .caption(document.getCaption())
                .permalink(document.getPermalink())
                .problemCategories(immutableList(document.getTaskCategories()))
                .build();
    }
}
