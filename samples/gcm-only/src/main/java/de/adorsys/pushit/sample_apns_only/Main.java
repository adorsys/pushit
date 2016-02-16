package de.adorsys.pushit.sample_apns_only;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.adorsys.pushit.Dispatcher;
import de.adorsys.pushit.MessageBuilder;
import de.adorsys.pushit.Receiver;
import de.adorsys.pushit.gcm.GcmSender;

/**
 * @author Christoph Dietze
 */
public class Main {

	private static final Config conf = ConfigFactory.load();
	private static String apiKey = conf.getString("gcm.apiKey");
	private static String registrationId = conf.getString("gcm.registrationId");

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Hello");

		GcmSender gcmSender = new GcmSender(apiKey);
		Dispatcher dispatcher = new Dispatcher.Builder().gcmSender(gcmSender).build();

		MessageBuilder.SimpleTextMessageBuilder messageBuilder = new MessageBuilder.SimpleTextMessageBuilder("Hi from pushit");

		Receiver receiver = new Receiver.Builder().addGcmToken(registrationId).build();
		dispatcher.send(messageBuilder, receiver);
		System.out.println("Done");
	}
}
