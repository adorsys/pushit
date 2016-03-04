package de.adorsys.pushit.apns;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Objects;

/**
 * Wrapper class around {@link ApnsService}.
 * <p/>
 * The wrapping is neccessary to avoid NoClassDefFoundErrors when using pushit without APNs.
 *
 * @author Christoph Dietze
 */
public class ApnsSender {

    public static ApnsSender create(String keyFilename, String keyPassphrase) {
        try {
            ApnsService apnsService = APNS.newService().withCert(Objects.requireNonNull(keyFilename), Objects.requireNonNull(keyPassphrase))
                    .withProductionDestination().build();
            return new ApnsSender(apnsService);
        } catch (NoClassDefFoundError e) {
            throw new RuntimeException(
                    "Classes required by APNs implementation of pushit not found, did you include the required dependency?", e);
        }
    }

    private static final Logger log = LoggerFactory.getLogger(ApnsSender.class);

    private final ApnsService apnsService;

    public ApnsSender(ApnsService apnsService) {
        this.apnsService = Objects.requireNonNull(apnsService);
    }

    /**
     * @return The low-level instance of java-apns' {@link ApnsService}.
     */
    public ApnsService getApnsService() {
        return apnsService;
    }

    public void send(String payload, String apnsToken) {
        log.debug("Sending: {} to {}", payload, apnsToken);
        apnsService.push(apnsToken, payload);
    }

    public void bulkSend(String payload, Collection<String> apnsTokens) {
        log.debug("Sending: {} to {}", payload, apnsTokens);
        apnsService.push(apnsTokens, payload);
    }
}
