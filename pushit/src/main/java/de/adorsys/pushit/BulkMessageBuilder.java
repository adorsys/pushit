package de.adorsys.pushit;

import com.google.android.gcm.server.Message;
import com.notnoop.apns.APNS;
import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmSender;

/**
 * @author Christoph Dietze
 */
public interface BulkMessageBuilder {

	String buildApnsMessage(ApnsSender sender);

	Message buildGcmMessage(GcmSender sender);

	class SimpleText implements BulkMessageBuilder {

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
