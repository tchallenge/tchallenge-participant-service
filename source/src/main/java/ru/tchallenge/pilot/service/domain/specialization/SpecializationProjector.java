package ru.tchallenge.pilot.service.domain.specialization;

import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.GenericProjector;

@ManagedComponent
public class SpecializationProjector extends GenericProjector {

    public Specialization specialization(final SpecializationDocument document) {
        return Specialization.builder()
                .id(document.getId())
                .caption(document.getCaption())
                .permalink(document.getPermalink())
                .problemCategories(immutableList(document.getProblemCategories()))
                .build();
    }
}
