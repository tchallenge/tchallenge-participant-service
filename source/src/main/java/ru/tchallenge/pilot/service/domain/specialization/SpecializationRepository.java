package ru.tchallenge.pilot.service.domain.specialization;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.in;

import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.GenericRepository;

@ManagedComponent
public class SpecializationRepository extends GenericRepository {

    public List<SpecializationDocument> findAll() {
        return documents()
                .find()
                .map(SpecializationDocument::new)
                .into(new ArrayList<>());
    }

    public List<SpecializationDocument> findByPermalinks(final List<String> permalinks) {
        return documents()
                .find()
                .filter(in("permalink", permalinks))
                .map(SpecializationDocument::new)
                .into(new ArrayList<>());
    }

    @Override
    protected String getCollectionName() {
        return "specializations";
    }
}
