package de.adorsys.pushit.gcm;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Christoph Dietze
 */
public class GcmSender {
	private static final Logger log = LoggerFactory.getLogger(GcmSender.class);

	private final String apiKey;
	private final Sender sender;

	public GcmSender(String apiKey) {
		this.apiKey = apiKey;
		sender = new Sender(apiKey);
	}

	/** @return the low-level GCM {@link com.google.android.gcm.server.Sender}. */
	public Sender getSender() {
		return sender;
	}

	public void send(Message message, String gcmToken) {
		int numOfRetries = 0;
		try {
			log.info("Sending: {}", message);
			Result result = sender.send(message, gcmToken, numOfRetries);
			log.info("Result: {}", result);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
