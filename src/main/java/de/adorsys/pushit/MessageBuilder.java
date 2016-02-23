package de.adorsys.pushit;

import com.google.android.gcm.server.Message;
import com.notnoop.apns.APNS;
import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmSender;

/**
 * @author Christoph Dietze
 */
public interface MessageBuilder {

	String buildApnsMessage(ApnsSender sender, String apnsToken);

	com.google.android.gcm.server.Message buildGcmMessage(GcmSender sender, String gcmToken);

	class SimpleTextMessageBuilder implements MessageBuilder {

		private final String text;

		public SimpleTextMessageBuilder(String text) {
			this.text = text;
		}

		@Override
		public String buildApnsMessage(ApnsSender sender, String apnsToken) {
			return APNS.newPayload().alertBody(text).build();
		}

		@Override
		public Message buildGcmMessage(GcmSender sender, String gcmToken) {
			return new Message.Builder().addData("message", "this is the message").addData("other-parameter", "some value").build();
		}
	}
}
