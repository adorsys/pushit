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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmSender;

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

        ApnsSender apnsSender = ApnsSender.create(keyFilename, keyPassphrase);

        GcmSender gcmSender = GcmSender.create(apiKey);

        Dispatcher dispatcher = new Dispatcher.Builder().apnsSender(apnsSender).gcmSender(gcmSender).build();

        Message.TextMessage message = new Message.TextMessage("Hi from pushit");

        Receiver receiver = new Receiver.Builder().addApnsToken(deviceToken).addGcmToken(registrationId).build();

        dispatcher.send(message, receiver);
    }
}
