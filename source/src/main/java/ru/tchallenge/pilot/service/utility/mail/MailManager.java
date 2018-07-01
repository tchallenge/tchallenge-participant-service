package ru.tchallenge.pilot.service.utility.mail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;

import com.sendgrid.*;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

@Slf4j
public final class MailManager {

    public static MailManager INSTANCE = new MailManager();

    private final String mime;
    private final String origin;
    private final SendGrid sendgrid;
    private final boolean sendgridEnabled;
    private final Session session;

    {
        this.mime = "text/html";
        this.origin = "info@t-challenge.ru";
        this.sendgridEnabled = sendgridEnabled();
        this.sendgrid = this.sendgridEnabled ? sendgrid() : null;
        this.session = this.sendgridEnabled ? null : session();
    }

    public void send(final MailInvoice invoice) {
        if (this.sendgridEnabled) {
            sendViaSendgrid(invoice);
        } else {
            sendViaLocalService(invoice);
        }
    }

    private void sendViaLocalService(final MailInvoice invoice) {
        try {
            final MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(origin));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(invoice.getEmail()));
            message.setSubject(invoice.getSubject(), "UTF-8");
            message.setContent(invoice.getContent(), "text/html;charset=utf-8");
            Transport.send(message);
        } catch (final Exception exception) {
            throw wrapped(exception);
        }
    }

    private void sendViaSendgrid(final MailInvoice invoice) {
        try {
            final Request request = sendgridRequest(invoice);
            sendgrid.api(request);
        } catch (final IOException exception) {
            throw wrapped(exception);
        }
    }

    private Request sendgridRequest(final MailInvoice invoice) {
        try {
            final Request result = new Request();
            final Mail mail = sendgridMail(invoice);
            result.setMethod(Method.POST);
            result.setEndpoint("mail/send");
            result.setBody(mail.build());
            return result;
        } catch (final IOException exception) {
            throw wrapped(exception);
        }
    }

    private Mail sendgridMail(final MailInvoice invoice) {
        final Email from = new Email(origin);
        final String subject = invoice.getSubject();
        final Email to = new Email(invoice.getEmail());
        final Content content = new Content(mime, invoice.getContent());
        return new Mail(from, subject, to, content);
    }

    private SendGrid sendgrid() {
        return new SendGrid(sendgridApiKey(), sendgridClient());
    }

    private boolean sendgridEnabled() {
        final String property = System.getenv("TCHALLENGE_SENDGRID_ENABLED");
        log.info("TCHALLENGE_SENDGRID_ENABLED: " + property);
        return property != null && Boolean.parseBoolean(property);
    }

    private String sendgridApiKey() {
        final String property = System.getenv("TCHALLENGE_SENDGRID_APIKEY");
        log.info("TCHALLENGE_SENDGRID_APIKEY: " + property);
        return property;
    }

    private Client sendgridClient() {
        return new Client(httpClient());
    }

    private CloseableHttpClient httpClient() {
        final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        final String proxyConfiguration = System.getenv("TCHALLENGE_PROXY");
        if (proxyConfiguration != null) {
            httpClientBuilder.setProxy(httpProxy(proxyConfiguration));
        }
        return httpClientBuilder.build();
    }

    private HttpHost httpProxy(final String proxyConfiguration) {
        return HttpHost.create(proxyConfiguration);
    }

    private Session session() {
        final Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "localhost");
        return Session.getDefaultInstance(properties);
    }

    private RuntimeException wrapped(final Exception exception) {
        return new RuntimeException(exception);
    }
}
