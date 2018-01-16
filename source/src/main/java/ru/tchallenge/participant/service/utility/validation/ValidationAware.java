package ru.tchallenge.participant.service.utility.validation;

import com.google.common.collect.Lists;

import java.util.Collection;

public interface ValidationAware {

    default Collection<String> collectViolations() {
        final Collection<String> violations = Lists.newArrayList();
        registerViolations(violations);
        return violations;
    }

    default boolean valid() {
        return collectViolations().isEmpty();
    }

    default boolean notValid() {
        return !valid();
    }

    default void validate() {
        final Collection<String> violations = collectViolations();
        if (!violations.isEmpty()) {
            throw new RuntimeException("Object is not valid");
        }
    }

    void registerViolations(final Collection<String> violations);
}
