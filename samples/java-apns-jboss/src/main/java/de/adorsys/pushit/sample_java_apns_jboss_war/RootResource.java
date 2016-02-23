package de.adorsys.pushit.sample_java_apns_jboss_war;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Christoph Dietze
 */
@Path("")
public class RootResource {

	@GET
	public String get() {
		sendMessage();
		return "Message successfully sent!";
	}

	private static final Config conf = ConfigFactory.load();
	private static final String keyFileName = conf.getString("apns.keyFile");
	private static final String keyPassphrase = conf.getString("apns.keyPassphrase");
	private static final String topic = conf.getString("apns.topic");
	private static final String deviceToken = conf.getString("apns.deviceToken");

	private void sendMessage() {
		ApnsService service = APNS.newService().withCert(keyFileName, keyPassphrase).withProductionDestination().build();

		String payload = APNS.newPayload().alertBody("Hi from sample_java_apns_jboss").build();
		service.push(deviceToken, payload);
	}
}
