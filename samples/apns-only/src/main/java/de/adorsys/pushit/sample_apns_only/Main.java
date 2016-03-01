package de.adorsys.pushit.sample_apns_only;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.adorsys.pushit.Dispatcher;
import de.adorsys.pushit.Message;
import de.adorsys.pushit.PersonalizedMessage;
import de.adorsys.pushit.Receiver;
import de.adorsys.pushit.apns.ApnsSender;

/**
 * @author Christoph Dietze
 */
public class Main {

	private static final Config conf = ConfigFactory.load();
	private static final String keyFilename = conf.getString("apns.keyFile");
	private static final String keyPassphrase = conf.getString("apns.keyPassphrase");
	private static final String deviceToken = conf.getString("apns.deviceToken");

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Hello");

		ApnsSender apnsSender = new ApnsSender(keyFilename, keyPassphrase);
		Dispatcher dispatcher = new Dispatcher.Builder().apnsSender(apnsSender).build();

		Message.TextMessage message = new Message.TextMessage("Hi from pushit");

		Receiver receiver = new Receiver.Builder().addApnsToken(deviceToken).build();
		dispatcher.send(message, receiver);
		System.out.println("Done");
	}
}
