#!/bin/sh

### Don't run this script; it's just a temporary solution to copy stuff over from my local maven repository until I get
### those jars on the maven central repo.

SCRIPT_DIR="$( cd "$( dirname "$0" )" && pwd )"
COMMONS_VERSION="1.1.0"
HTTP_VERSION="1.0.0"

# Delete all the libs
rm -rf ${SCRIPT_DIR}/*.jar

# Copy them over from maven
cp ~/.m2/repository/com/droplr/droplr-common/${COMMONS_VERSION}/droplr-common-${COMMONS_VERSION}.jar ${SCRIPT_DIR}
cp ~/.m2/repository/com/droplr/droplr-common-network/${COMMONS_VERSION}/droplr-common-network-${COMMONS_VERSION}.jar ${SCRIPT_DIR}
cp ~/.m2/repository/com/droplr/droplr-http/${HTTP_VERSION}/droplr-http-${HTTP_VERSION}.jar ${SCRIPT_DIR}
