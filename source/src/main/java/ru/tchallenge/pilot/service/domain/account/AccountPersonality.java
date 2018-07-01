package ru.tchallenge.pilot.service.domain.account;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.pilot.service.utility.validation.ValidationAware;

import java.util.Collection;

@Data
@Builder
public final class AccountPersonality implements ValidationAware {

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

    @Override
    public void registerViolations(final Collection<String> violations) {

    }
}
