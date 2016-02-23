package de.adorsys.pushit;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmSender;

import java.io.File;

/**
 * @author Christoph Dietze
 */
public class TestCommonMessageMain {

	private static final Config conf = ConfigFactory.load();
	private static final String keyFilename = conf.getString("apns.keyFile");
	private static final String keyPassphrase = conf.getString("apns.keyPassphrase");
	private static final String deviceToken = conf.getString("apns.deviceToken");
	private static final String apiKey = conf.getString("gcm.apiKey");
	private static final String registrationId = conf.getString("gcm.registrationId");

	public static void main(String[] args) {

		ApnsSender apnsSender = new ApnsSender(keyFilename, keyPassphrase);

		GcmSender gcmSender = new GcmSender(apiKey);

		Dispatcher dispatcher = new Dispatcher.Builder().apnsSender(apnsSender).gcmSender(gcmSender).build();

		MessageBuilder.SimpleTextMessageBuilder messageBuilder = new MessageBuilder.SimpleTextMessageBuilder("Hi from pushit");

		Receiver receiver = new Receiver.Builder().addApnsToken(deviceToken).addGcmToken(registrationId).build();

		dispatcher.send(messageBuilder, receiver);
	}
}
