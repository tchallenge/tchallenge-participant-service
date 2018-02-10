package ru.tchallenge.participant.service.utility.mail;

import ru.tchallenge.participant.service.utility.batch.BatchManager;
import ru.tchallenge.participant.service.utility.template.TemplateManager;

public final class TemplateMailManager {

    public static TemplateMailManager INSTANCE = new TemplateMailManager();

    private final BatchManager batchManager = BatchManager.INSTANCE;
    private final MailManager mailManager = MailManager.INSTANCE;
    private final TemplateManager templateManager = TemplateManager.INSTANCE;

    private TemplateMailManager() {

    }

    public void sendAsync(final TemplateMailInvoice invoice) {
        batchManager.submit(() -> send(invoice));
    }

    public void send(final TemplateMailInvoice invoice) {
        final String templatePath = templatePath(invoice.getTemplateName());
        final String content = templateManager.render(templatePath, invoice.getData());
        final MailInvoice mailInvoice = MailInvoice.builder()
                .email(invoice.getEmail())
                .subject(invoice.getSubject())
                .content(content)
                .build();
        mailManager.send(mailInvoice);
    }

    private String templatePath(final String templateName) {
        return "mail/templates/" + templateName;
    }
}