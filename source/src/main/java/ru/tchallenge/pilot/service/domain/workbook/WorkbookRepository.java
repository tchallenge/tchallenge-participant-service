package ru.tchallenge.pilot.service.domain.workbook;

import ru.tchallenge.pilot.service.utility.data.GenericRepository;

public final class WorkbookRepository extends GenericRepository {

    public static final WorkbookRepository INSTANCE = new WorkbookRepository();

    private WorkbookRepository() {
        super("workbooks");
    }
}
