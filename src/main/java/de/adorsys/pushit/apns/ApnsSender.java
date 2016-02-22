package de.adorsys.pushit.apns;

import com.relayrides.pushy.apns.ApnsClient;
import com.relayrides.pushy.apns.ApnsPushNotification;
import com.relayrides.pushy.apns.PushNotificationResponse;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Christoph Dietze
 */
public class ApnsSender {
	private static final Logger log = LoggerFactory.getLogger(ApnsSender.class);

	private final ApnsConfig config;
	private final ApnsClient<ApnsPushNotification> apnsClient;

	public ApnsSender(ApnsConfig config) {
		this.config = config;
		try {
			apnsClient = new ApnsClient<>(config.getKeyFile(), config.getPassphrase());
			Future<Void> connectFuture = apnsClient.connect(ApnsClient.PRODUCTION_APNS_HOST);
			connectFuture.await();
		} catch (SSLException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (NoClassDefFoundError e) {
			throw new RuntimeException("Classes required by APNS backend of pushit not found", e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ApnsConfig getConfig() {
		return config;
	}

	public void send(SimpleApnsPushNotification message, String apnsToken) {
		try {
			log.info("Sending: {}", message);
			PushNotificationResponse<ApnsPushNotification> response = apnsClient.sendNotification(message).get();
			log.info("Received response: {}", response);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	/** @return The low-level instance of pushy's {@link ApnsClient}. */
	public ApnsClient<ApnsPushNotification> getApnsClient() {
		return apnsClient;
	}

	public void disconnect() {
		try {
			apnsClient.disconnect().await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
