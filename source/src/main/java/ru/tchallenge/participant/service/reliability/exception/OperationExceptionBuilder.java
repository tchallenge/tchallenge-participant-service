package ru.tchallenge.participant.service.reliability.exception;

import javax.annotation.Nullable;

public final class OperationExceptionBuilder {

    public static OperationExceptionBuilder operationException() {
        return new OperationExceptionBuilder();
    }

    private Object attachment;
    private Throwable cause;
    private String description;
    private String textcode;

    public OperationExceptionBuilder attachment(final @Nullable Object attachment) {
        this.attachment = attachment;
        return this;
    }

    public OperationExceptionBuilder cause(final @Nullable Throwable cause) {
        this.cause = cause;
        return this;
    }

    public OperationExceptionBuilder description(final @Nullable String description) {
        this.description = description;
        return this;
    }

    public OperationExceptionBuilder textcode(final String textcode) {
        this.textcode = textcode;
        return this;
    }

    public OperationException build() {
        return new OperationException(
                this.textcode,
                this.description,
                this.attachment,
                this.cause);
    }
}
