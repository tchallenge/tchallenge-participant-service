package ru.tchallenge.participant.service.reliability.exception;

import java.util.Objects;
import javax.annotation.Nullable;

public class OperationException extends RuntimeException {

    private final Object attachment;
    private final String description;
    private final String textcode;

    public OperationException(final String textcode,
                              final @Nullable String description,
                              final @Nullable Object attachment,
                              final @Nullable Throwable cause) {
        super(cause);
        this.attachment = attachment;
        this.description = description;
        Objects.requireNonNull(textcode, "Operation exception textcode cannot be null");
        this.textcode = textcode;
    }

    @Nullable
    public Object getAttachment() {
        return this.attachment;
    }

    @Nullable
    public String getDescription() {
        return this.description;
    }

    public String getTextcode() {
        return this.textcode;
    }

    @Override
    public String getMessage() {
        final String description = this.description != null ? this.description : "No description available";
        return String.format("%s: %s", this.textcode, description);
    }
}
