package ru.tchallenge.pilot.service.domain.workbook;

import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.GenericRepository;

@ManagedComponent
public class WorkbookRepository extends GenericRepository {

    @Override
    protected String getCollectionName() {
        return "workbooks";
    }
}
