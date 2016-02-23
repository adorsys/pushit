FROM docker.adorsys.de/centos7/eap:6.4-oraclejdk-8

# Add admin/admin user
COPY mgmt-users.properties ${JBOSS_HOME}/standalone/configuration/

# Add the apns certificate to the image, must match `apns.keyFile` in the config
# COPY CSIPushCertTestAndKey.p12 /

EXPOSE 8080 8787 9990 9999

RUN echo "JAVA_OPTS=\"\${JAVA_OPTS} -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n\"" >> ${JBOSS_HOME}/bin/standalone.conf
