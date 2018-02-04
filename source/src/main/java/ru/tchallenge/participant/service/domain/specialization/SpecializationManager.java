package ru.tchallenge.participant.service.domain.specialization;

import java.util.List;
import java.util.stream.Collectors;

public final class SpecializationManager {

    public static final SpecializationManager INSTANCE = new SpecializationManager();

    private final SpecializationProjector specializationProjector = SpecializationProjector.INSTANCE;
    private final SpecializationRepository specializationRepository = SpecializationRepository.INSTANCE;

    public List<Specialization> retrieveByAll() {
        return specializationRepository
                .findAll()
                .stream()
                .map(specializationProjector::specialization)
                .collect(Collectors.toList());
    }

    private SpecializationManager() {

    }
}
