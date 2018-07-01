package ru.tchallenge.pilot.service.domain.problem.image;

import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.GenericProjector;

@ManagedComponent
public class ProblemImageProjector extends GenericProjector {

    public ProblemImage problemImage(final ProblemImageDocument document) {
        return ProblemImage.builder()
                .binaryId(document.getBinaryId())
                .format(document.getFormat())
                .height(document.getHeight())
                .width(document.getWidth())
                .build();
    }
}
