package ru.tchallenge.pilot.service.domain.problem.image;

import ru.tchallenge.pilot.service.utility.data.GenericProjector;

public final class ProblemImageProjector extends GenericProjector {

    public static final ProblemImageProjector INSTANCE = new ProblemImageProjector();

    private ProblemImageProjector() {

    }

    public ProblemImage problemImage(final ProblemImageDocument document) {
        return ProblemImage.builder()
                .binaryId(document.getBinaryId())
                .format(document.getFormat())
                .height(document.getHeight())
                .width(document.getWidth())
                .build();
    }
}
