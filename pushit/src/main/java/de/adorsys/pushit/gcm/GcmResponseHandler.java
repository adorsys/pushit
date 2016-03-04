package de.adorsys.pushit.gcm;

/**
 * @author Christoph Dietze
 */
public interface GcmResponseHandler {
    void handleGcmResponse(GcmResponse gcmResponse);

    void handleGcmMultiResponse(GcmMultiResponse gcmMultiResponse);
}
