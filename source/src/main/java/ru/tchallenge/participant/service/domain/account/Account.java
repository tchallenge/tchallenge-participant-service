package ru.tchallenge.participant.service.domain.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {

    private String id;
    private String email;
    private String passwordHash;
    private AccountPersonality personality;
    private String status;
}
