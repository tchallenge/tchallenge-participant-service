package ru.tchallenge.participant.service.domain.problem;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public final class ProblemRandomInvoice {

    private Set<String> categories;
    private Set<String> difficulties;
    private Integer limit;
}
