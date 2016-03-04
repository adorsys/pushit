package de.adorsys.pushit.gcm;

import com.google.android.gcm.server.Message;

import java.util.Objects;

/**
 * Wrapper class around {@link Message}.
 * <p/>
 * The wrapping is neccessary to avoid NoClassDefFoundErrors when using pushit without GCM.
 *
 * @author Christoph Dietze
 */
public class GcmMessage {
    private final Message message;

    public GcmMessage(Message message) {
        this.message = Objects.requireNonNull(message);
    }

    public Message gcmMessage() {
        return message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append("{");
        sb.append("message=").append(message);
        sb.append("}");
        return sb.toString();
    }
}
