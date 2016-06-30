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
package de.adorsys.pushit.gcm;

import com.google.android.gcm.server.Message;

import java.util.Objects;

/**
 * Wrapper class around {@link Message}.
 * <p/>
 * The wrapping is neccessary to avoid NoClassDefFoundErrors when using pushit without GCM.
 *
 * @author Christoph Dietze
 */
public class GcmMessage {
    private final Message message;

    public GcmMessage(Message message) {
        this.message = Objects.requireNonNull(message);
    }

    public Message gcmMessage() {
        return message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append("{");
        sb.append("message=").append(message);
        sb.append("}");
        return sb.toString();
    }
}
