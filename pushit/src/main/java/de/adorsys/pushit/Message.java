package de.adorsys.pushit;

import com.notnoop.apns.APNS;
import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmMessage;
import de.adorsys.pushit.gcm.GcmSender;

/**
 * Interface for push messages.
 * <p/>
 * In contrast to {@link Message} you do not have the personal deviceToken available when building the message.
 *
 * @author Christoph Dietze
 */
public interface Message {

    /**
     * @return the message to be sent via APNs or null if not supported.
     */
    String apnsMessage(ApnsSender sender);

    /**
     * @return the message to be sent via GCM or null if not supported.
     */
    GcmMessage gcmMessage(GcmSender sender);

    class TextMessage implements Message {

        private final String text;

        public TextMessage(String text) {
            this.text = text;
        }

        @Override
        public String apnsMessage(ApnsSender sender) {
            return APNS.newPayload().alertBody(text).build();
        }

        @Override
        public GcmMessage gcmMessage(GcmSender sender) {
            return new GcmMessage(new com.google.android.gcm.server.Message.Builder().addData("message", text).build());
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
            sb.append("{");
            sb.append("text=").append(text);
            sb.append("}");
            return sb.toString();
        }
    }

    class BasicMessage implements Message {

        private final String apnsMessage;
        private final GcmMessage gcmMessage;

        public BasicMessage(String apnsMessage, GcmMessage gcmMessage) {
            this.apnsMessage = apnsMessage;
            this.gcmMessage = gcmMessage;
        }

        @Override
        public String apnsMessage(ApnsSender sender) {
            return apnsMessage;
        }

        @Override
        public GcmMessage gcmMessage(GcmSender sender) {
            return gcmMessage;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
            sb.append("{");
            sb.append("apnsMessage=").append(apnsMessage);
            sb.append(", gcmMessage=").append(gcmMessage);
            sb.append("}");
            return sb.toString();
        }
    }
}
