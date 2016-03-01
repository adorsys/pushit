package de.adorsys.pushit;

import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmSender;

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

	public void send(Message message, Receiver receiver) {
		if (apnsSender != null) {
			for (String apnsToken : receiver.getApnsTokens()) {
				String apnsMessage = message.apnsMessage(apnsSender);
				apnsSender.send(apnsMessage, apnsToken);
			}
		}
		if (gcmSender != null) {
			for (String gcmToken : receiver.getGcmTokens()) {
				com.google.android.gcm.server.Message gcmMessage = message.gcmMessage(gcmSender);
				gcmSender.send(gcmMessage, gcmToken);
			}
		}
	}

	public void send(PersonalizedMessage personalizedMessage, Receiver receiver) {
		if (apnsSender != null) {
			for (String apnsToken : receiver.getApnsTokens()) {
				String apnsMessage = personalizedMessage.apnsMessage(apnsSender, apnsToken);
				apnsSender.send(apnsMessage, apnsToken);
			}
		}
		if (gcmSender != null) {
			for (String gcmToken : receiver.getGcmTokens()) {
				com.google.android.gcm.server.Message message = personalizedMessage.gcmMessage(gcmSender, gcmToken);
				gcmSender.send(message, gcmToken);
			}
		}
	}

	public void bulkSend(Message message, Receiver receiver) {
		if (apnsSender != null) {
			String apnsMessage = message.apnsMessage(apnsSender);
			apnsSender.bulkSend(apnsMessage, receiver.getApnsTokens());
		}
		if (gcmSender != null) {
			com.google.android.gcm.server.Message gcmMessage = message.gcmMessage(gcmSender);
			gcmSender.bulkSend(gcmMessage, receiver.getGcmTokens());
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
