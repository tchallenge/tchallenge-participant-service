package ru.tchallenge.participant.service.domain.maturity;

import org.bson.Document;

public final class MaturityMapper {

    public static final MaturityMapper INSTANCE = new MaturityMapper();

    public Maturity intoMaturity(final Document document) {
        return Maturity.builder()
                .caption(document.getString("caption"))
                .textcode(document.getString("textcode"))
                .build();
    }

    private MaturityMapper() {

    }
}
