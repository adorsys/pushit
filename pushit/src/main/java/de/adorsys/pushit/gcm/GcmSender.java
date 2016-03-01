package de.adorsys.pushit.gcm;

import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Christoph Dietze
 */
public class GcmSender {
	private static final Logger log = LoggerFactory.getLogger(GcmSender.class);

	private final String apiKey;
	private final Sender sender;

	public GcmSender(String apiKey) {
		this.apiKey = Objects.requireNonNull(apiKey);
		try {
			sender = new Sender(apiKey);
		} catch (NoClassDefFoundError e) {
			throw new RuntimeException(
					"Classes required by GCM implementation of pushit not found, did you include the required dependency?", e);
		}
	}

	/** @return the low-level GCM {@link com.google.android.gcm.server.Sender}. */
	public Sender getSender() {
		return sender;
	}

	public void send(GcmMessage message, String gcmToken) {
		Objects.requireNonNull(message);
		Objects.requireNonNull(gcmToken);
		try {
			log.debug("Sending: {} to {}", message, gcmToken);
			Result result = sender.sendNoRetry(message.gcmMessage(), gcmToken);
			log.debug("Result: {}", result);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void bulkSend(GcmMessage message, Collection<String> gcmTokens) {
		Objects.requireNonNull(message);
		Objects.requireNonNull(gcmTokens);
		try {
			log.debug("Sending: {} to {}", message, gcmTokens);
			MulticastResult result = sender.sendNoRetry(message.gcmMessage(), new ArrayList<>(gcmTokens));
			log.debug("Result: {}", result);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
