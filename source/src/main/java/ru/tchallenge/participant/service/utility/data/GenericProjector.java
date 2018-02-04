package ru.tchallenge.participant.service.utility.data;

import java.util.List;

import com.google.common.collect.ImmutableList;

public abstract class GenericProjector {

    protected <T> ImmutableList<T> immutableList(final List<T> list) {
        return ImmutableList.copyOf(list);
    }
}
