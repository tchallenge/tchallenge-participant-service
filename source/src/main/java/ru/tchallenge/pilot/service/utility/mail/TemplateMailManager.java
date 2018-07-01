package ru.tchallenge.pilot.service.utility.mail;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.batch.BatchManager;
import ru.tchallenge.pilot.service.utility.template.TemplateManager;

@ManagedComponent
public class TemplateMailManager extends GenericApplicationComponent {

    private BatchManager batchManager;
    private MailManager mailManager;
    private TemplateManager templateManager;

    public void sendAsync(TemplateMailInvoice invoice) {
        this.batchManager.submit(() -> send(invoice));
    }

    public void send(TemplateMailInvoice invoice) {
        String templatePath = templatePath(invoice.getTemplateName());
        String content = this.templateManager.render(templatePath, invoice.getData());
        MailInvoice mailInvoice = MailInvoice.builder()
                .email(invoice.getEmail())
                .subject(invoice.getSubject())
                .content(content)
                .build();
        this.mailManager.send(mailInvoice);
    }

    private String templatePath(final String templateName) {
        return "mail/templates/" + templateName;
    }

    @Override
    public void init() {
        super.init();
        this.batchManager = getComponent(BatchManager.class);
        this.mailManager = getComponent(MailManager.class);
        this.templateManager = getComponent(TemplateManager.class);
    }
}
