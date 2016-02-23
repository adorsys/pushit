package de.adorsys.pushit;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Christoph Dietze
 */
public class TestGcmMain {

	private static final Logger LOG = LoggerFactory.getLogger(TestGcmMain.class);

	private static final Config conf = ConfigFactory.load();
	private static final String apiKey = conf.getString("gcm.apiKey");
	private static final String registrationId = conf.getString("gcm.registrationId");

	public static void main(String[] args) throws IOException {
		Sender sender = new Sender(apiKey);
		Message message = new Message.Builder().addData("message", "this is the message").addData("other-parameter", "some value").build();
		int numOfRetries = 0;
		Result result = sender.send(message, registrationId, numOfRetries);
		LOG.info("Result: {}", result);
	}
}
