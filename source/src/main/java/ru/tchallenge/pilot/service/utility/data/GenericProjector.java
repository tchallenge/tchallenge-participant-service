package ru.tchallenge.pilot.service.utility.data;

import java.util.List;

import com.google.common.collect.ImmutableList;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;

public abstract class GenericProjector extends GenericApplicationComponent {

    protected <T> ImmutableList<T> immutableList(final List<T> list) {
        return ImmutableList.copyOf(list);
    }
}
