package ru.tchallenge.participant.service.domain.account;

import org.bson.Document;

public final class AccountMapper {

    public static final AccountMapper INSTANCE = new AccountMapper();

    public Account intoAccount(final Document document) {
        return Account.builder()
                .id(document.getObjectId("_id").toHexString())
                .status(document.getString("status"))
                .email(document.getString("email"))
                .personality(intoAccountPersonality((Document) document.get("personality")))
                .build();
    }

    public Account intoAccountShort(final Document document) {
        return Account.builder()
                .id(document.getObjectId("_id").toHexString())
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

    private AccountMapper() {

    }
}
