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
import de.adorsys.pushit.gcm.GcmSender;

/**
 * Interface for personalized push messages.
 * In contrast to {@link Message} you have the personal deviceToken available when building the message.
 *
 * @author Christoph Dietze
 */
public interface PersonalizedMessage {

	/**
	 * @param sender sender
	 * @param apnsToken apnsToken
	 * @return the message to be sent via APNs or null if not supported.
	 */
	String apnsMessage(ApnsSender sender, String apnsToken);

	/**
	 * @param sender sender
	 * @param gcmToken gcmToken
	 * @return the message to be sent via GCM or null if not supported.
	 */
	GcmMessage gcmMessage(GcmSender sender, String gcmToken);
}
