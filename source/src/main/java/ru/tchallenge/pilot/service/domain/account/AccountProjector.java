package ru.tchallenge.pilot.service.domain.account;

import org.bson.Document;

import ru.tchallenge.pilot.service.utility.data.DocumentWrapper;
import ru.tchallenge.pilot.service.utility.data.GenericProjector;

public final class AccountProjector extends GenericProjector {

    public static final AccountProjector INSTANCE = new AccountProjector();

    public Account intoAccount(final Document document) {
        return Account.builder()
                .id(new DocumentWrapper(document).getId())
                .status(document.getString("status"))
                .email(document.getString("email"))
                .personality(intoAccountPersonality((Document) document.get("personality")))
                .registeredAt(document.getDate("registeredAt").toInstant())
                .build();
    }

    public Account intoAccountShort(final Document document) {
        return Account.builder()
                .id(new DocumentWrapper(document).getId())
                .status(document.getString("status"))
                .email(document.getString("email"))
                .build();
    }

    private AccountPersonality intoAccountPersonality(final Document document) {
        if (document == null) {
            return null;
        }
        return AccountPersonality.builder()
                .firstname(document.getString("firstname"))
                .lastname(document.getString("lastname"))
                .middlename(document.getString("middlename"))
                .quickname(document.getString("quickname"))
                .essay(document.getString("essay"))
                .linkedin(document.getString("linkedin"))
                .hh(document.getString("hh"))
                .github(document.getString("github"))
                .bitbucket(document.getString("bitbucket"))
                .website(document.getString("website"))
                .build();
    }

    private AccountProjector() {

    }
}
