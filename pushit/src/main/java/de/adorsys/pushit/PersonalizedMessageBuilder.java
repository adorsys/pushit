package de.adorsys.pushit;

import com.google.android.gcm.server.Message;
import com.notnoop.apns.APNS;
import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmSender;

/**
 * A Builder for push messages sent to a single device.
 * <p/>
 * In contrast to {@link MessageBuilder} you have the personal deviceToken available when building the message.
 *
 * @author Christoph Dietze
 */
public interface PersonalizedMessageBuilder {

	/**
	 * @return the message to be sent via APNs or null if not supported.
	 */
	String buildApnsMessage(ApnsSender sender, String apnsToken);

	/**
	 * @return the message to be sent via GCM or null if not supported.
	 */
	com.google.android.gcm.server.Message buildGcmMessage(GcmSender sender, String gcmToken);

	class SimpleText implements PersonalizedMessageBuilder {

		private final String text;

		public SimpleText(String text) {
			this.text = text;
		}

		@Override
		public String buildApnsMessage(ApnsSender sender, String apnsToken) {
			return APNS.newPayload().alertBody(text).build();
		}

		@Override
		public Message buildGcmMessage(GcmSender sender, String gcmToken) {
			return new Message.Builder().addData("message", text).build();
		}
	}
}
