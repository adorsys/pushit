package de.adorsys.pushit;

import com.google.android.gcm.server.Message;
import com.notnoop.apns.APNS;
import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmSender;

/**
 * A Builder for push messages.
 * <p/>
 * In contrast to {@link MessageBuilder} you do not have the personal deviceToken available when building the message.
 *
 * @author Christoph Dietze
 */
public interface MessageBuilder {

	/**
	 * @return the message to be sent via APNs or null if not supported.
	 */
	String buildApnsMessage(ApnsSender sender);

	/**
	 * @return the message to be sent via GCM or null if not supported.
	 */
	Message buildGcmMessage(GcmSender sender);

	class SimpleText implements MessageBuilder {

		private final String text;

		public SimpleText(String text) {
			this.text = text;
		}

		@Override
		public String buildApnsMessage(ApnsSender sender) {
			return APNS.newPayload().alertBody(text).build();
		}

		@Override
		public Message buildGcmMessage(GcmSender sender) {
			return new Message.Builder().addData("message", text).build();
		}
	}
}
