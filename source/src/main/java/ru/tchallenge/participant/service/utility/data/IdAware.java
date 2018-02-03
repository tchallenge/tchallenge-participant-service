package ru.tchallenge.participant.service.utility.data;

public interface IdAware {

    Id getId();

    default IdAware justId() {
        return IdContainer.builder()
                .id(getId())
                .build();
    }
}
