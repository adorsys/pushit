# pushit

## Usage

Add the BOM to your maven dependencyManagement:
```xml
    <dependencyManagement>
      <dependencies>
        <dependency>
          <groupId>de.adorsys.pushit</groupId>
          <artifactId>bom</artifactId>
          <version>0.1.0</version>
          <type>pom</type>
          <scope>import</scope>
        </dependency>
      </dependencies>
    </dependencyManagement>
```
This defines the versions for all the pushit artefact as well as the apns and gcm implementations.
You can now add the dependencies:
```xml
    <dependency>
      <groupId>de.adorsys.pushit</groupId>
      <artifactId>pushit</artifactId>
    </dependency>
    <!-- Only needed if you want to push to apns (iOS) -->
    <dependency>
      <groupId>com.notnoop.apns</groupId>
      <artifactId>apns</artifactId>
    </dependency>
    <!-- Only needed if you want to push to GCM (Android) -->
    <dependency>
      <groupId>com.google.gcm</groupId>
      <artifactId>gcm-server</artifactId>
    </dependency>
```

Example to send a simple message to an iOS and Android device:
```java
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.adorsys.pushit.apns.ApnsSender;
import de.adorsys.pushit.gcm.GcmSender;

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
```