#
# Copyright (C) 2015 Christoph Dietze (cdi@adorsys.de)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#         http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

FROM docker.adorsys.de/centos7/eap:6.4-oraclejdk-8

# Add admin/admin user
COPY mgmt-users.properties ${JBOSS_HOME}/standalone/configuration/

# Add the apns certificate to the image, must match `apns.keyFile` in the config
# COPY CSIPushCertTestAndKey.p12 /

EXPOSE 8080 8787 9990 9999

RUN echo "JAVA_OPTS=\"\${JAVA_OPTS} -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n\"" >> ${JBOSS_HOME}/bin/standalone.conf
