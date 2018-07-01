package ru.tchallenge.pilot.service.security.voucher;

import lombok.Builder;
import lombok.Data;

import spark.Request;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.mail.TemplateMailInvoice;
import ru.tchallenge.pilot.service.utility.mail.TemplateMailManager;
import ru.tchallenge.pilot.service.utility.template.TemplateManager;

@ManagedComponent
public class SecurityVoucherFacade extends GenericApplicationComponent {

    private SecurityVoucherManager securityVoucherManager;
    private TemplateManager templateManager;
    private TemplateMailManager templateMailManager;

    public SecurityVoucher createAndSend(Request request, SecurityVoucherInvoice invoice) {
        final SecurityVoucher voucher = securityVoucherManager.create(invoice.getEmail());
        final String backlink = createBacklink(invoice, voucher);
        final TemplateMailInvoice templateMailInvoice = TemplateMailInvoice.builder()
                .email(invoice.getEmail())
                .subject("T-Challenge: Login Link")
                .templateName("security-voucher-created")
                .data(MailData.builder().backlink(backlink).build())
                .build();
        templateMailManager.sendAsync(templateMailInvoice);
        return voucher;
    }

    @Override
    public void init() {
        super.init();
        this.securityVoucherManager = getComponent(SecurityVoucherManager.class);
        this.templateManager = getComponent(TemplateManager.class);
        this.templateMailManager = getComponent(TemplateMailManager.class);
    }

    private String createBacklink(final SecurityVoucherInvoice invoice, final SecurityVoucher voucher) {
        return templateManager.renderInline(invoice.getBacklinkTemplate(), voucher);
    }

    @Data
    @Builder
    private static final class MailData {
        private final String backlink;
    }
}
