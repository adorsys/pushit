package de.adorsys.pushit.apns;

import java.io.File;

/**
 * @author Christoph Dietze
 */
public class ApnsConfig {
	private final File keyFile;
	private final String passphrase;
	private final String topic;

	private ApnsConfig(Builder builder) {
		this.keyFile = builder.keyFile;
		this.passphrase = builder.passphrase;
		this.topic = builder.topic;
		if (keyFile == null)
			throw new NullPointerException("keyFile must not be null");
		if (!keyFile.isFile()) {
			throw new AssertionError(String.format("Key file %s not found.", keyFile.getAbsoluteFile()));
		}
		if (passphrase == null)
			throw new NullPointerException("passphrase must not be null");
		if (topic == null)
			throw new NullPointerException("topic must not be null");
	}

	public File getKeyFile() {
		return keyFile;
	}

	public String getPassphrase() {
		return passphrase;
	}

	public String getTopic() {
		return topic;
	}

	public static class Builder {
		private File keyFile;
		private String passphrase;
		private String topic;

		public Builder keyFile(File keyFile) {
			this.keyFile = keyFile;
			return this;
		}

		public Builder passphrase(String passphrase) {
			this.passphrase = passphrase;
			return this;
		}

		public Builder topic(String topic) {
			this.topic = topic;
			return this;
		}

		public ApnsConfig build() {
			return new ApnsConfig(this);
		}
	}
}
