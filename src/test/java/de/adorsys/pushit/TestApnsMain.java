package de.adorsys.pushit;

import com.relayrides.pushy.apns.ApnsClient;
import com.relayrides.pushy.apns.PushNotificationResponse;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.relayrides.pushy.apns.util.TokenUtil;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Christoph Dietze
 */
public class TestApnsMain {

	private static final Logger LOG = LoggerFactory.getLogger(TestApnsMain.class);

	private static final Config conf = ConfigFactory.load();
	private static final String keyFileName = conf.getString("apns.keyFile");
	private static final String keyPassphrase = conf.getString("apns.keyPassphrase");
	private static final String topic = conf.getString("apns.topic");
	private static final String deviceToken = conf.getString("apns.deviceToken");

	public static void main(String[] args) throws Exception {
		File keyFile = new File(keyFileName);
		if (!keyFile.isFile()) {
			throw new AssertionError(String.format("Key file %s not found.", keyFile.getAbsoluteFile()));
		}
		ApnsClient<SimpleApnsPushNotification> apnsClient = new ApnsClient<>(keyFile, keyPassphrase);

		Future<Void> connectFuture = apnsClient.connect(ApnsClient.PRODUCTION_APNS_HOST);
		connectFuture.await();

		ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
		payloadBuilder.setAlertBody("Example!");
		String payload = payloadBuilder.buildWithDefaultMaximumLength();
		String token = TokenUtil.sanitizeTokenString(deviceToken);
		SimpleApnsPushNotification notification = new SimpleApnsPushNotification(token, topic, payload);

		PushNotificationResponse<SimpleApnsPushNotification> response = apnsClient.sendNotification(notification).get();

		LOG.info("Received response: {}", response);
		apnsClient.disconnect().await();
	}
}
