package ru.tchallenge.participant.service.domain.specialization;

import com.google.common.collect.Lists;

import java.util.List;

public final class SpecializationManager {

    public static final SpecializationManager INSTANCE = new SpecializationManager();

    public List<Specialization> retrieveByTextcodes(final String... textcodes) {
        return specializationRepository
                .findByTextcodes(textcodes)
                .map(specializationMapper::intoSpecialization)
                .into(Lists.newArrayList());
    }

    private final SpecializationMapper specializationMapper = SpecializationMapper.INSTANCE;
    private final SpecializationRepository specializationRepository = SpecializationRepository.INSTANCE;

    private SpecializationManager() {

    }
}
