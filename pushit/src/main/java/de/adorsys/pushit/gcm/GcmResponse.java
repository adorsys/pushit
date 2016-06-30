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
import com.google.android.gcm.server.Result;

import java.util.Objects;

/**
 * Wrapper class around {@link Result}.
 * <p/>
 * Also contains additional related data, i.e. the gcmToken and the {@link Message}.
 *
 * @author Christoph Dietze
 * @see <a href="https://developers.google.com/cloud-messaging/http#response">Google's documentation for the response format</a>
 * @see {@link GcmMultiResponse}
 */
public class GcmResponse {
	private final Message message;
	private final String gcmToken;
	private final Result result;

	public GcmResponse(Message message, String gcmToken, Result result) {
		this.message = Objects.requireNonNull(message);
		this.gcmToken = Objects.requireNonNull(gcmToken);
		this.result = Objects.requireNonNull(result);
	}

	public Message getMessage() {
		return message;
	}

	public String getGcmToken() {
		return gcmToken;
	}

	public Result getResult() {
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("{");
		sb.append("gcmToken=").append(gcmToken);
		sb.append("result=").append(result);
		sb.append("}");
		return sb.toString();
	}
}
