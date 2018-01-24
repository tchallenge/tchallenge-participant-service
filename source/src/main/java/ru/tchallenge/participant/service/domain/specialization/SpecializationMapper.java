package ru.tchallenge.participant.service.domain.specialization;

import org.bson.Document;

public final class SpecializationMapper {

    public static final SpecializationMapper INSTANCE = new SpecializationMapper();

    public Specialization intoSpecialization(final Document document) {
        return Specialization.builder()
                .caption(document.getString("caption"))
                .textcode(document.getString("textcode"))
                .build();
    }

    private SpecializationMapper() {

    }
}
