package ru.tchallenge.pilot.service.domain.specialization;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.in;

import ru.tchallenge.pilot.service.utility.data.GenericRepository;

public final class SpecializationRepository extends GenericRepository {

    public static final SpecializationRepository INSTANCE = new SpecializationRepository();

    private SpecializationRepository() {
        super("specializations");
    }

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
}
