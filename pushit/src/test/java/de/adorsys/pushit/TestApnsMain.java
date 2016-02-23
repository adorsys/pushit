package de.adorsys.pushit;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christoph Dietze
 */
public class TestApnsMain {

	private static final Logger LOG = LoggerFactory.getLogger(TestApnsMain.class);

	private static final Config conf = ConfigFactory.load();
	private static final String keyFilename = conf.getString("apns.keyFile");
	private static final String keyPassphrase = conf.getString("apns.keyPassphrase");
	private static final String deviceToken = conf.getString("apns.deviceToken");

	public static void main(String[] args) throws Exception {
		ApnsService service = APNS.newService().withCert(keyFilename, keyPassphrase).withProductionDestination().build();

		String payload = APNS.newPayload().alertBody("Hi from sample_java_apns_jboss").build();
		service.push(deviceToken, payload);

		LOG.info("Message sent");
	}
}
