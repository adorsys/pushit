POC project to send a APNS message using [java-apns](https://github.com/notnoop/java-apns) in a JBoss EAP 6.4 Server.
Does not use pushit.

Usage:
* Adjust config and add certificate to docker image
* Build and run docker image: `docker-compose build && docker-compose up`
* Build and deploy test app: `mvn clean install jboss-as:deploy`
* Use a browser or the commandline to trigger a send: `curl http://docker:8080/sample-java-apns-jboss-0.1-SNAPSHOT`