package ru.tchallenge.pilot.service.utility.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

public final class TemplateManager {

    public static final TemplateManager INSTANCE = new TemplateManager();

    private final Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader());

    private TemplateManager() {

    }

    public String render(final String path, final Object model) {
        try {
            return handlebars.compile(path).apply(model);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public String renderInline(final String template, final Object model) {
        try {
            return handlebars.compileInline(template).apply(model);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
