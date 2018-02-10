package ru.tchallenge.participant.service.utility.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

public final class TemplateManager {

    public static final TemplateManager INSTANCE = new TemplateManager();

    private final Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader());

    private TemplateManager() {

    }

    public String render(final String templateName, final Object model) {
        try {
            return handlebars.compile(templateName).apply(model);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
