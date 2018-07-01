package ru.tchallenge.pilot.service.domain.specialization;

import java.util.List;
import java.util.stream.Collectors;

import spark.Request;

public final class SpecializationManager {

    public static final SpecializationManager INSTANCE = new SpecializationManager();

    private final SpecializationProjector specializationProjector = SpecializationProjector.INSTANCE;
    private final SpecializationRepository specializationRepository = SpecializationRepository.INSTANCE;

    public List<Specialization> retrieveByAll(Request request) {
        return specializationRepository
                .findAll()
                .stream()
                .map(specializationProjector::specialization)
                .collect(Collectors.toList());
    }

    private SpecializationManager() {

    }
}
