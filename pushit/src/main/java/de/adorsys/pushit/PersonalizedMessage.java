package de.adorsys.pushit;

import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmMessage;
import de.adorsys.pushit.gcm.GcmSender;

/**
 * Interface for personalized push messages.
 * <p/>
 * In contrast to {@link Message} you have the personal deviceToken available when building the message.
 *
 * @author Christoph Dietze
 */
public interface PersonalizedMessage {

	/**
	 * @return the message to be sent via APNs or null if not supported.
	 */
	String apnsMessage(ApnsSender sender, String apnsToken);

	/**
	 * @return the message to be sent via GCM or null if not supported.
	 */
	GcmMessage gcmMessage(GcmSender sender, String gcmToken);
}
