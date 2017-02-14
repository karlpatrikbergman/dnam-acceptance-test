#!/usr/bin/env bash
sleep 120
exec java -Djava.security.egd=file:/dev/./urandom -jar /dnam-acceptance-test.jar

