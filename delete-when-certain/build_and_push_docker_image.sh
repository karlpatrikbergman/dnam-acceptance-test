#!/usr/bin/env bash

export PS4='+(${BASH_SOURCE}:${LINENO}): ${FUNCNAME[0]:+${FUNCNAME[0]}(): }'
set -x

if [ "$#" -ne 4 ]; then
    echo "Illegal number of parameters"
    echo "Usage: ${0} <path-to-application-jar> <docker-image-name> <docker-image-version> <docker-registry>"
    echo "Example: ${0} ../../../build/libs/alarmservice-1.0-SNAPSHOT.jar 1.0-SNAPSHOT localhost:5000"
    echo ""
    exit 1
fi

readonly PATH_TO_APPLICATION_JAR=$1
readonly DOCKER_IMAGE_NAME=$2
readonly DOCKER_IMAGE_VERSION=$3
readonly DOCKER_REGISTRY=$4

readonly TAG="${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_VERSION}"
readonly APPLICATION_JAR=$(basename $1) # Get file name from file path

cp ${PATH_TO_APPLICATION_JAR} ${APPLICATION_JAR}
docker build -t ${TAG} --build-arg alarmservice_app=${APPLICATION_JAR} .
docker push ${TAG}
yes | rm ${APPLICATION_JAR}