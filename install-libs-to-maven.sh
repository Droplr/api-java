#!/bin/sh

VERSION="1.0.0"

mvn install:install-file -Dfile=lib/http-client-${VERSION}.jar \
                         -DgroupId=com.biasedbit \
                         -DartifactId=http-client \
                         -Dversion=${VERSION} \
                         -Dpackaging=jar \
                         -DgeneratePom=true
