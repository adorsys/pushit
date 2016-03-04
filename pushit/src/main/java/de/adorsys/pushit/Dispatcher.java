package de.adorsys.pushit;

import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmMessage;
import de.adorsys.pushit.gcm.GcmResponse;
import de.adorsys.pushit.gcm.GcmResponseHandler;
import de.adorsys.pushit.gcm.GcmSender;

import java.util.Objects;

/**
 * @author Christoph Dietze
 */
public class Dispatcher {
	/** may be null */
	private final ApnsSender apnsSender;
	/** may be null */
	private final GcmSender gcmSender;
	/** may be null */
	private GcmResponseHandler gcmResponseHandler;

	private Dispatcher(Builder builder) {
		this.apnsSender = builder.apnsSender;
		this.gcmSender = builder.gcmSender;
	}

	public Dispatcher setGcmResponseHandler(GcmResponseHandler gcmResponseHandler) {
		this.gcmResponseHandler = gcmResponseHandler;
		return this;
	}

	public void send(Message message, Receiver receiver) {
		Objects.requireNonNull(message);
		Objects.requireNonNull(receiver);
		if (apnsSender != null) {
			for (String apnsToken : receiver.getApnsTokens()) {
				String apnsMessage = message.apnsMessage(apnsSender);
				if (apnsMessage != null) {
					apnsSender.send(apnsMessage, apnsToken);
				}
			}
		}
		if (gcmSender != null) {
			for (String gcmToken : receiver.getGcmTokens()) {
				GcmMessage gcmMessage = message.gcmMessage(gcmSender);
				if (gcmMessage != null) {
					gcmSender.send(gcmMessage, gcmToken, gcmResponseHandler);
				}
			}
		}
	}

	public void send(PersonalizedMessage personalizedMessage, Receiver receiver) {
		Objects.requireNonNull(personalizedMessage);
		Objects.requireNonNull(receiver);
		if (apnsSender != null) {
			for (String apnsToken : receiver.getApnsTokens()) {
				String apnsMessage = personalizedMessage.apnsMessage(apnsSender, apnsToken);
				if (apnsMessage != null) {
					apnsSender.send(apnsMessage, apnsToken);
				}
			}
		}
		if (gcmSender != null) {
			for (String gcmToken : receiver.getGcmTokens()) {
				GcmMessage gcmMessage = personalizedMessage.gcmMessage(gcmSender, gcmToken);
				if (gcmMessage != null) {
					gcmSender.send(gcmMessage, gcmToken, gcmResponseHandler);
				}
			}
		}
	}

	public void bulkSend(Message message, Receiver receiver) {
		Objects.requireNonNull(message);
		Objects.requireNonNull(receiver);
		if (apnsSender != null) {
			String apnsMessage = message.apnsMessage(apnsSender);
			if (apnsMessage != null) {
				apnsSender.bulkSend(apnsMessage, receiver.getApnsTokens());
			}
		}
		if (gcmSender != null) {
			GcmMessage gcmMessage = message.gcmMessage(gcmSender);
			if (gcmMessage != null) {
				gcmSender.bulkSend(gcmMessage, receiver.getGcmTokens(), gcmResponseHandler);
			}
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
