package ru.tchallenge.participant.service.utility.data;

public interface IdAware {

    String getId();

    default IdAware justId() {
        return IdContainer.builder()
                .id(getId())
                .build();
    }
}
