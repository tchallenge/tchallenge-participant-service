package ru.tchallenge.pilot.service.security.registration;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.pilot.service.security.voucher.SecurityVoucher;
import ru.tchallenge.pilot.service.security.voucher.SecurityVoucherManager;
import ru.tchallenge.pilot.service.utility.mail.TemplateMailInvoice;
import ru.tchallenge.pilot.service.utility.mail.TemplateMailManager;
import ru.tchallenge.pilot.service.utility.template.TemplateManager;

public final class SecurityRegistrationFacade {

    public static final SecurityRegistrationFacade INSTANCE = new SecurityRegistrationFacade();

    private final SecurityRegistrationManager securityRegistrationManager = SecurityRegistrationManager.INSTANCE;
    private final SecurityVoucherManager securityVoucherManager = SecurityVoucherManager.INSTANCE;
    private final TemplateManager templateManager = TemplateManager.INSTANCE;
    private final TemplateMailManager templateMailManager = TemplateMailManager.INSTANCE;

    private SecurityRegistrationFacade() {

    }

    public SecurityRegistration createAndSendVoucher(final SecurityRegistrationInvoice invoice) {
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
