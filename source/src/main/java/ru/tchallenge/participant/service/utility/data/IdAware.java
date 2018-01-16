package ru.tchallenge.participant.service.utility.data;

public interface IdAware {

    String getId();

    default IdContainer justId() {
        return IdContainer.builder()
                .id(getId())
                .build();
    }
}
