package ru.tchallenge.pilot.service.security.voucher;

import lombok.Builder;
import lombok.Data;

import ru.tchallenge.pilot.service.utility.mail.TemplateMailInvoice;
import ru.tchallenge.pilot.service.utility.mail.TemplateMailManager;
import ru.tchallenge.pilot.service.utility.template.TemplateManager;

public final class SecurityVoucherFacade {

    public static final SecurityVoucherFacade INSTANCE = new SecurityVoucherFacade();

    private final SecurityVoucherManager securityVoucherManager = SecurityVoucherManager.INSTANCE;
    private final TemplateManager templateManager = TemplateManager.INSTANCE;
    private final TemplateMailManager templateMailManager = TemplateMailManager.INSTANCE;

    private SecurityVoucherFacade() {

    }

    public SecurityVoucher createAndSend(final SecurityVoucherInvoice invoice) {
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

    private String createBacklink(final SecurityVoucherInvoice invoice, final SecurityVoucher voucher) {
        return templateManager.renderInline(invoice.getBacklinkTemplate(), voucher);
    }

    @Data
    @Builder
    private static final class MailData {
        private final String backlink;
    }
}
