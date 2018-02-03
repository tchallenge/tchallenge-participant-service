package ru.tchallenge.participant.service.domain.maturity;

import org.bson.Document;
import ru.tchallenge.participant.service.utility.persistence.GenericProjector;

public final class MaturityProjector extends GenericProjector {

    public static final MaturityProjector INSTANCE = new MaturityProjector();

    public Maturity intoMaturity(final Document document) {
        return Maturity.builder()
                .caption(document.getString("caption"))
                .textcode(document.getString("textcode"))
                .build();
    }

    private MaturityProjector() {

    }
}
