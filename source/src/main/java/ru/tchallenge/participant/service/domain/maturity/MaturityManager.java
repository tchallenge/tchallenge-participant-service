package ru.tchallenge.participant.service.domain.maturity;

import com.google.common.collect.Lists;

import java.util.List;

public final class MaturityManager {

    public static final MaturityManager INSTANCE = new MaturityManager();

    public List<Maturity> retrieveByTextcodes(final String... textcodes) {
        return maturityRepository
                .findByTextcodes(textcodes)
                .map(maturityProjector::intoMaturity)
                .into(Lists.newArrayList());
    }

    private final MaturityProjector maturityProjector = MaturityProjector.INSTANCE;
    private final MaturityRepository maturityRepository = MaturityRepository.INSTANCE;

    private MaturityManager() {

    }
}
