package ru.tchallenge.participant.service.domain.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountPersonality {

    private String firstname;
    private String lastname;
    private String middlename;
    private String quickname;
    private String essay;
    private String linkedin;
    private String hh;
    private String github;
    private String bitbucket;
    private String website;
}
