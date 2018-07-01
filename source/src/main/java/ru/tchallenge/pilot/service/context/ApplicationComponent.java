package ru.tchallenge.pilot.service.context;

public interface ApplicationComponent {

    ApplicationContext getContext();

    void setContext(ApplicationContext context);

    void init();
}
