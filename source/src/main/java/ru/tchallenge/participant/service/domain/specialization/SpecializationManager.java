package ru.tchallenge.participant.service.domain.specialization;

import java.util.List;
import java.util.stream.Collectors;

public final class SpecializationManager {

    public static final SpecializationManager INSTANCE = new SpecializationManager();

    public List<Specialization> retrieveByAll() {
        return specializationRepository
                .findAll()
                .stream()
                .map(specializationProjector::specialization)
                .collect(Collectors.toList());
    }

    public List<Specialization> retrieveByPermalinks(final String... permalinks) {
        return specializationRepository
                .findByPermalinks(permalinks)
                .stream()
                .map(specializationProjector::specialization)
                .collect(Collectors.toList());
    }

    private final SpecializationProjector specializationProjector = SpecializationProjector.INSTANCE;
    private final SpecializationRepository specializationRepository = SpecializationRepository.INSTANCE;

    private SpecializationManager() {

    }
}
