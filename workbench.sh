#!/usr/bin/env bash

docker rm -f dnam-acceptance-test
./gradlew clean build
docker-compose -f docker-compose.yml build dnam-acceptance-test
##docker run --name dnam-acceptance-test --network=dnamacceptancetest_dnam_network --ip 172.35.0.130 localhost:5000/dnam-acceptance-test