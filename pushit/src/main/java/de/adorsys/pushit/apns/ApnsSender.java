/**
 * Copyright (C) 2015 Christoph Dietze (cdi@adorsys.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
