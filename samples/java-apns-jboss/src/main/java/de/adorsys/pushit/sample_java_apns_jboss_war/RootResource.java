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
	private static final String keyFilename = conf.getString("apns.keyFile");
	private static final String keyPassphrase = conf.getString("apns.keyPassphrase");
	private static final String topic = conf.getString("apns.topic");
	private static final String deviceToken = conf.getString("apns.deviceToken");

	private void sendMessage() {
		ApnsService service = APNS.newService().withCert(keyFilename, keyPassphrase).withProductionDestination().build();

		String payload = APNS.newPayload().alertBody("Hi from sample_java_apns_jboss").build();
		service.push(deviceToken, payload);
	}
}
