package de.adorsys.pushit;

import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmSender;

import java.util.Collections;

/**
 * @author Christoph Dietze
 */
public class Dispatcher {

	private final ApnsSender apnsSender;
	private final GcmSender gcmSender;

	private Dispatcher(Builder builder) {
		this.apnsSender = builder.apnsSender;
		this.gcmSender = builder.gcmSender;
	}

	public void send(MessageBuilder messageBuilder, Receiver receiver) {
		if (apnsSender != null) {
			for (String apnsToken : receiver.getApnsTokens()) {
				String apnsMessage = messageBuilder.buildApnsMessage(apnsSender, apnsToken);
				apnsSender.send(apnsMessage, Collections.singleton(apnsToken));
			}
		}
		if (gcmSender != null) {
			for (String gcmToken : receiver.getGcmTokens()) {
				com.google.android.gcm.server.Message message = messageBuilder.buildGcmMessage(gcmSender, gcmToken);
				gcmSender.send(message, Collections.singleton(gcmToken));
			}
		}
	}

	public void bulkSend(BulkMessageBuilder messageBuilder, Receiver receiver) {
		if (apnsSender != null) {
			String apnsMessage = messageBuilder.buildApnsMessage(apnsSender);
			apnsSender.send(apnsMessage, receiver.getApnsTokens());
		}
		if (gcmSender != null) {
			com.google.android.gcm.server.Message message = messageBuilder.buildGcmMessage(gcmSender);
			gcmSender.send(message, receiver.getGcmTokens());
		}
	}

	public static class Builder {

		private ApnsSender apnsSender;
		private GcmSender gcmSender;

		public Builder apnsSender(ApnsSender apnsSender) {
			this.apnsSender = apnsSender;
			return this;
		}

		public Builder gcmSender(GcmSender gcmSender) {
			this.gcmSender = gcmSender;
			return this;
		}

		public Dispatcher build() {
			return new Dispatcher(this);
		}
	}
}
