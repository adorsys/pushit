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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Christoph Dietze
 */
public class Receiver {

	private final List<String> apnsTokens;
	private final List<String> gcmTokens;

	private Receiver(Builder builder) {
		this.apnsTokens = Collections.unmodifiableList(new ArrayList<>(builder.apnsTokens));
		this.gcmTokens = Collections.unmodifiableList(new ArrayList<>(builder.gcmTokens));
	}

	public List<String> getApnsTokens() {
		return apnsTokens;
	}

	public List<String> getGcmTokens() {
		return gcmTokens;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("{");
		sb.append("apnsTokens=").append(apnsTokens);
		sb.append(", gcmTokens=").append(gcmTokens);
		sb.append("}");
		return sb.toString();
	}

	public static class Builder {
		private final List<String> apnsTokens;
		private final List<String> gcmTokens;

		public Builder() {
			apnsTokens = new ArrayList<>();
			gcmTokens = new ArrayList<>();
		}

		public Builder(Receiver prototype) {
			apnsTokens = new ArrayList<>(prototype.apnsTokens);
			gcmTokens = new ArrayList<>(prototype.gcmTokens);
		}

		public Builder addApnsToken(String apnsToken) {
			this.apnsTokens.add(apnsToken);
			return this;
		}

		public Builder addApnsTokens(Collection<String> apnsTokens) {
			this.apnsTokens.addAll(apnsTokens);
			return this;
		}

		public Builder addGcmToken(String gcmToken) {
			gcmTokens.add(gcmToken);
			return this;
		}

		public Builder addGcmTokens(Collection<String> gcmTokens) {
			this.gcmTokens.addAll(gcmTokens);
			return this;
		}

		public Receiver build() {
			return new Receiver(this);
		}
	}
}
