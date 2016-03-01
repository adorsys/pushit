package de.adorsys.pushit;

import com.notnoop.apns.APNS;
import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmSender;

/**
 * Interface for push messages.
 * <p/>
 * In contrast to {@link Message} you do not have the personal deviceToken available when building the message.
 *
 * @author Christoph Dietze
 */
public interface Message {

	/**
	 * @return the message to be sent via APNs or null if not supported.
	 */
	String apnsMessage(ApnsSender sender);

	/**
	 * @return the message to be sent via GCM or null if not supported.
	 */
	com.google.android.gcm.server.Message gcmMessage(GcmSender sender);

	class TextMessage implements Message {

		private final String text;

		public TextMessage(String text) {
			this.text = text;
		}

		@Override
		public String apnsMessage(ApnsSender sender) {
			return APNS.newPayload().alertBody(text).build();
		}

		@Override
		public com.google.android.gcm.server.Message gcmMessage(GcmSender sender) {
			return new com.google.android.gcm.server.Message.Builder().addData("message", text).build();
		}
	}

	class BasicMessage implements Message {

		private final String apnsMessage;
		private final com.google.android.gcm.server.Message gcmMessage;

		public BasicMessage(String apnsMessage, com.google.android.gcm.server.Message gcmMessage) {
			this.apnsMessage = apnsMessage;
			this.gcmMessage = gcmMessage;
		}

		@Override
		public String apnsMessage(ApnsSender sender) {
			return apnsMessage;
		}

		@Override
		public com.google.android.gcm.server.Message gcmMessage(GcmSender sender) {
			return gcmMessage;
		}
	}
}
