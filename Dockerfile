FROM centos:7
RUN yum update -y && \
yum install -y wget && \
yum install -y java-1.8.0-openjdk-headless && \
yum clean all
VOLUME /tmp
# We should make sure to place higher those files that are less likely to change.
COPY "wait-for-it.sh" "/wait-for-it.sh"
COPY "entrypoint.sh" "/entrypoint.sh"
COPY ["build/libs/dnam-acceptance-test-1.0-SNAPSHOT.jar", "/dnam-acceptance-test.jar"]
ENTRYPOINT ["/bin/bash","./entrypoint.sh"]