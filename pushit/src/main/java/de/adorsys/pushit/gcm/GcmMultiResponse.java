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
 * see &lt;a href="https://developers.google.com/cloud-messaging/http#response"&gt;Google's documentation for the response format&lt;/a&gt;
 * @see GcmResponse
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
