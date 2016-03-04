package de.adorsys.pushit.gcm;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;

import java.util.List;
import java.util.Objects;

/**
 * Wrapper class around {@link MulticastResult}.
 *
 * @author Christoph Dietze
 * @see <a href="https://developers.google.com/cloud-messaging/http#response">Google's documentation for the response format</a>
 * @see {@link GcmResponse}
 */
public class GcmMultiResponse {
	private final Message message;
	/** The gcmTokens of the recipients. This list corresponds 1:1 to {@code multicastResult.getResults()}. */
	private final List<String> gcmTokens;
	private final MulticastResult multicastResult;

	public GcmMultiResponse(Message message, List<String> gcmTokens, MulticastResult multicastResult) {
		this.message = Objects.requireNonNull(message);
		this.gcmTokens = Objects.requireNonNull(gcmTokens);
		this.multicastResult = Objects.requireNonNull(multicastResult);
	}

	public Message getMessage() {
		return message;
	}

	public List<String> getGcmTokens() {
		return gcmTokens;
	}

	public MulticastResult getMulticastResult() {
		return multicastResult;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("{");
		sb.append("gcmTokens=").append(gcmTokens);
		sb.append("multicastResult=").append(multicastResult);
		sb.append("}");
		return sb.toString();
	}
}
