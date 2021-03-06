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
package de.adorsys.pushit.sample_apns_only;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.adorsys.pushit.Dispatcher;
import de.adorsys.pushit.Message;
import de.adorsys.pushit.PersonalizedMessage;
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

        GcmSender gcmSender = GcmSender.create(apiKey);
        Dispatcher dispatcher = new Dispatcher.Builder().gcmSender(gcmSender).build();

        Message.TextMessage message = new Message.TextMessage("Hi from pushit");

        Receiver receiver = new Receiver.Builder().addGcmToken(registrationId).build();
        dispatcher.send(message, receiver);
        System.out.println("Done");
    }
}
