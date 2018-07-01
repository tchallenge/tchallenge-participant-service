package ru.tchallenge.pilot.service.utility.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;

@ManagedComponent
public class TemplateManager extends GenericApplicationComponent {

    private Handlebars handlebars;

    public String render(final String path, final Object model) {
        try {
            return this.handlebars.compile(path).apply(model);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public String renderInline(final String template, final Object model) {
        try {
            return this.handlebars.compileInline(template).apply(model);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void init() {
        super.init();
        this.handlebars = new Handlebars(new ClassPathTemplateLoader());
    }
}
