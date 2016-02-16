package de.adorsys.pushit;

import com.google.android.gcm.server.Message;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.relayrides.pushy.apns.util.TokenUtil;
import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmSender;

/**
 * @author Christoph Dietze
 */
public interface MessageBuilder {

	SimpleApnsPushNotification buildApnsMessage(ApnsSender sender, String apnsToken);

	com.google.android.gcm.server.Message buildGcmMessage(GcmSender sender, String gcmToken);

	class SimpleTextMessageBuilder implements MessageBuilder {

		private final String text;

		public SimpleTextMessageBuilder(String text) {
			this.text = text;
		}

		@Override
		public SimpleApnsPushNotification buildApnsMessage(ApnsSender sender, String apnsToken) {
			ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
			payloadBuilder.setAlertBody(text);
			String payload = payloadBuilder.buildWithDefaultMaximumLength();
			String token = TokenUtil.sanitizeTokenString(apnsToken);
			return new SimpleApnsPushNotification(token, sender.getConfig().getTopic(), payload);
		}

		@Override
		public Message buildGcmMessage(GcmSender sender, String gcmToken) {
			return new Message.Builder().addData("message", "this is the message").addData("other-parameter", "some value").build();
		}
	}
}
