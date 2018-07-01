package ru.tchallenge.pilot.service.context;

import java.util.*;

import org.reflections.Reflections;

public class ApplicationContext {

    private Map<Class<? extends ApplicationComponent>, ApplicationComponent> components;

    public ApplicationContext() {

    }

    public <T extends ApplicationComponent> T getComponent(Class<T> type) {
        return cast(this.components.get(type));
    }

    public void init() {
        this.components = new LinkedHashMap<>();
        for (Class<? extends ApplicationComponent> componentType : discoverComponentTypes()) {
            ApplicationComponent component = instantiateComponentType(componentType);
            component.setContext(this);
            this.components.put(component.getClass(), component);
        }
        for (ApplicationComponent component : this.components.values()) {
            component.init();
        }
    }

    private Collection<Class<? extends ApplicationComponent>> discoverComponentTypes() {
        Collection<Class<?>> componentTypes = new ArrayList<>();
        Reflections reflections = new Reflections("ru.tchallenge.pilot.service");
        componentTypes.addAll(reflections.getTypesAnnotatedWith(ManagedConfiguration.class));
        componentTypes.addAll(reflections.getTypesAnnotatedWith(ManagedComponent.class));
        return cast(componentTypes);
    }

    private ApplicationComponent instantiateComponentType(Class<? extends ApplicationComponent> componentType) {
        try {
            return componentType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T cast(Object object) {
        return (T) object;
    }
}
