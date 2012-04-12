#!/bin/sh

COMMONS_VERSION="1.0.7"
HTTP_VERSION="0.8.7"

mvn install:install-file -Dfile=lib/droplr-common-${COMMONS_VERSION}.jar \
                         -DgroupId=com.droplr \
                         -DartifactId=droplr-common \
                         -Dversion=${COMMONS_VERSION} \
                         -Dpackaging=jar \
                         -DgeneratePom=true

mvn install:install-file -Dfile=lib/droplr-common-network-${COMMONS_VERSION}.jar \
                         -DgroupId=com.droplr \
                         -DartifactId=droplr-common-network \
                         -Dversion=${COMMONS_VERSION} \
                         -Dpackaging=jar \
                         -DgeneratePom=true

mvn install:install-file -Dfile=lib/droplr-http-${HTTP_VERSION}.jar \
                         -DgroupId=com.droplr \
                         -DartifactId=droplr-http \
                         -Dversion=${HTTP_VERSION} \
                         -Dpackaging=jar \
                         -DgeneratePom=true
