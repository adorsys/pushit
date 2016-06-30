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
