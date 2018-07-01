package ru.tchallenge.pilot.service.domain.specialization;

import java.util.List;
import java.util.stream.Collectors;

import spark.Request;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;

@ManagedComponent
public class SpecializationManager extends GenericApplicationComponent {

    private SpecializationProjector specializationProjector;
    private SpecializationRepository specializationRepository;

    @Override
    public void init() {
        super.init();
        this.specializationProjector = getComponent(SpecializationProjector.class);
        this.specializationRepository = getComponent(SpecializationRepository.class);
    }

    public List<Specialization> retrieveByAll(Request request) {
        return specializationRepository
                .findAll()
                .stream()
                .map(specializationProjector::specialization)
                .collect(Collectors.toList());
    }
}
