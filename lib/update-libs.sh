#!/bin/sh

### Don't run this script; it's just a temporary solution to copy stuff over from my local maven repository until I get
### those jars on the maven central repo.

SCRIPT_DIR="$( cd "$( dirname "$0" )" && pwd )"
VERSION="1.0.0"

# Delete all the libs
rm -rf ${SCRIPT_DIR}/*.jar

# Copy them over from maven
cp ~/.m2/repository/com/biasedbit/http-client/${VERSION}/http-client-${VERSION}.jar ${SCRIPT_DIR}
