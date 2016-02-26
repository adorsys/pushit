package de.adorsys.pushit.apns;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author Christoph Dietze
 */
public class ApnsSender {
	private static final Logger log = LoggerFactory.getLogger(ApnsSender.class);

	private final ApnsService apnsService;

	public ApnsSender(String keyFilename, String keyPassphrase) {
		try {
			apnsService = APNS.newService().withCert(keyFilename, keyPassphrase).withProductionDestination().build();
		} catch (NoClassDefFoundError e) {
			throw new RuntimeException(
					"Classes required by APNs implementation of pushit not found, did you include the required dependency?", e);
		}
	}

	/** @return The low-level instance of java-apns' {@link ApnsService}. */
	public ApnsService getApnsService() {
		return apnsService;
	}

	public void send(String payload, Collection<String> apnsTokens) {
		log.debug("Sending: {} to {}", payload, apnsTokens);
		apnsService.push(apnsTokens, payload);
	}

}
