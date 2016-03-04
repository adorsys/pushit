package de.adorsys.pushit.gcm;

import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Christoph Dietze
 */
public class GcmSender {

    public static GcmSender create(String apiKey) {
        try {
            return new GcmSender(new Sender(apiKey));
        } catch (NoClassDefFoundError e) {
            throw new RuntimeException(
                    "Classes required by GCM implementation of pushit not found, did you include the required dependency?", e);
        }
    }

    private static final Logger log = LoggerFactory.getLogger(GcmSender.class);

    private final Sender sender;

    public GcmSender(Sender sender) {
        this.sender = Objects.requireNonNull(sender);
    }

    /**
     * @return the low-level GCM {@link com.google.android.gcm.server.Sender}.
     */
    public Sender getSender() {
        return sender;
    }

    public GcmResponse send(GcmMessage message, String gcmToken) {
        return send(message, gcmToken);
    }

    public GcmResponse send(GcmMessage message, String gcmToken, GcmResponseHandler gcmResponseHandler) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(gcmToken);
        try {
            log.debug("Sending: {} to {}", message, gcmToken);
            Result result = sender.sendNoRetry(message.gcmMessage(), gcmToken);
            log.debug("Result: {}", result);
            GcmResponse gcmResponse = new GcmResponse(message.gcmMessage(), gcmToken, result);
            if (gcmResponseHandler != null) gcmResponseHandler.handleGcmResponse(gcmResponse);
            return gcmResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GcmMultiResponse bulkSend(GcmMessage message, List<String> gcmTokens) {
        return bulkSend(message, gcmTokens, null);
    }

    public GcmMultiResponse bulkSend(GcmMessage message, List<String> gcmTokens, GcmResponseHandler gcmResponseHandler) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(gcmTokens);
        try {
            log.debug("Sending: {} to {}", message, gcmTokens);
            MulticastResult result = sender.sendNoRetry(message.gcmMessage(), new ArrayList<>(gcmTokens));
            log.debug("Result: {}", result);
            GcmMultiResponse gcmMultiResponse = new GcmMultiResponse(message.gcmMessage(), gcmTokens, result);
            if (gcmResponseHandler != null) {
                gcmResponseHandler.handleGcmMultiResponse(gcmMultiResponse);
            }
            return gcmMultiResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
