package ru.tchallenge.participant.service.domain.workbook;

import ru.tchallenge.participant.service.utility.persistence.GenericRepository;

public final class WorkbookRepository extends GenericRepository {

    public static final WorkbookRepository INSTANCE = new WorkbookRepository();

    private WorkbookRepository() {
        super("workbooks");
    }
}
