package ru.tchallenge.participant.service.domain.specialization;

import org.bson.Document;
import ru.tchallenge.participant.service.utility.data.GenericProjector;

public final class SpecializationProjector extends GenericProjector {

    public static final SpecializationProjector INSTANCE = new SpecializationProjector();

    public Specialization intoSpecialization(final Document document) {
        return Specialization.builder()
                .caption(document.getString("caption"))
                .textcode(document.getString("textcode"))
                .build();
    }

    private SpecializationProjector() {

    }
}
