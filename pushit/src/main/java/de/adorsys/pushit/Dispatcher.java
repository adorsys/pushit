/**
 * Copyright (C) 2015 Christoph Dietze (cdi@adorsys.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

	private Dispatcher(Builder builder) {
		this.apnsSender = builder.apnsSender;
		this.gcmSender = builder.gcmSender;
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
					gcmSender.send(gcmMessage, gcmToken);
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
					gcmSender.send(gcmMessage, gcmToken);
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
				gcmSender.bulkSend(gcmMessage, receiver.getGcmTokens());
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
