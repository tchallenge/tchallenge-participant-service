package ru.tchallenge.pilot.service.context;

public class GenericApplicationComponent implements ApplicationComponent {

    private ApplicationContext context;

    public <T extends ApplicationComponent> T getComponent(Class<T> type) {
        return this.context.getComponent(type);
    }

    @Override
    public ApplicationContext getContext() {
        return this.context;
    }

    @Override
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void init() {

    }
}
