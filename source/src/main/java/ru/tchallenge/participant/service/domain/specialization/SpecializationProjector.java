package ru.tchallenge.participant.service.domain.specialization;

import com.google.common.collect.ImmutableList;
import ru.tchallenge.participant.service.utility.data.GenericProjector;

public final class SpecializationProjector extends GenericProjector {

    public static final SpecializationProjector INSTANCE = new SpecializationProjector();

    public Specialization specialization(final SpecializationDocument document) {
        return Specialization.builder()
                .caption(document.getCaption())
                .permalink(document.getPermalink())
                .problemCategories(ImmutableList.copyOf(document.getTaskCategories()))
                .build();
    }

    private SpecializationProjector() {

    }
}
