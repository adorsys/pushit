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
