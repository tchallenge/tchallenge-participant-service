package ru.tchallenge.pilot.service.security.registration;

import lombok.Builder;
import lombok.Data;

import spark.Request;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.security.voucher.SecurityVoucher;
import ru.tchallenge.pilot.service.security.voucher.SecurityVoucherManager;
import ru.tchallenge.pilot.service.utility.mail.TemplateMailInvoice;
import ru.tchallenge.pilot.service.utility.mail.TemplateMailManager;
import ru.tchallenge.pilot.service.utility.template.TemplateManager;

@ManagedComponent
public class SecurityRegistrationFacade extends GenericApplicationComponent {

    private SecurityRegistrationManager securityRegistrationManager;
    private SecurityVoucherManager securityVoucherManager;
    private TemplateManager templateManager;
    private TemplateMailManager templateMailManager;

    @Override
    public void init() {
        super.init();
        this.securityRegistrationManager = getComponent(SecurityRegistrationManager.class);
        this.securityVoucherManager = getComponent(SecurityVoucherManager.class);
        this.templateManager = getComponent(TemplateManager.class);
        this.templateMailManager = getComponent(TemplateMailManager.class);
    }

    public SecurityRegistration createAndSendVoucher(Request request, SecurityRegistrationInvoice invoice) {
        final SecurityRegistration result = securityRegistrationManager.create(invoice);
        createVoucherAndSend(invoice);
        return result;
    }

    private SecurityVoucher createVoucherAndSend(final SecurityRegistrationInvoice invoice) {
        final SecurityVoucher voucher = securityVoucherManager.create(invoice.getEmail());
        final String backlink = createBacklink(invoice, voucher);
        final TemplateMailInvoice templateMailInvoice = TemplateMailInvoice.builder()
                .email(invoice.getEmail())
                .subject("T-Challenge: New Account")
                .templateName("account-created")
                .data(MailData.builder().backlink(backlink).build())
                .build();
        templateMailManager.sendAsync(templateMailInvoice);
        return voucher;
    }

    private String createBacklink(final SecurityRegistrationInvoice invoice, final SecurityVoucher voucher) {
        return templateManager.renderInline(invoice.getBacklinkTemplate(), voucher);
    }

    @Data
    @Builder
    private static final class MailData {
        private final String backlink;
    }
}
