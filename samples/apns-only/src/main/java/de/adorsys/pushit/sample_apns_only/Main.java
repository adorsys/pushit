package de.adorsys.pushit.sample_apns_only;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.adorsys.pushit.Dispatcher;
import de.adorsys.pushit.MessageBuilder;
import de.adorsys.pushit.Receiver;
import de.adorsys.pushit.apns.ApnsConfig;
import de.adorsys.pushit.apns.ApnsSender;

import java.io.File;

/**
 * @author Christoph Dietze
 */
public class Main {

	private static final Config conf = ConfigFactory.load();
	private static final String keyFileName = conf.getString("apns.keyFile");
	private static final String keyPassphrase = conf.getString("apns.keyPassphrase");
	private static final String topic = conf.getString("apns.topic");
	private static final String deviceToken = conf.getString("apns.deviceToken");

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Hello");
		ApnsConfig apnsConfig = new ApnsConfig.Builder().keyFile(new File(keyFileName)).passphrase(keyPassphrase).topic(topic).build();

		ApnsSender apnsSender = new ApnsSender(apnsConfig);
		Dispatcher dispatcher = new Dispatcher.Builder().apnsSender(apnsSender).build();

		MessageBuilder.SimpleTextMessageBuilder messageBuilder = new MessageBuilder.SimpleTextMessageBuilder("Hi from pushit");

		Receiver receiver = new Receiver.Builder().addApnsToken(deviceToken).build();
		dispatcher.send(messageBuilder, receiver);
		apnsSender.disconnect();
		System.out.println("Done");
	}
}
