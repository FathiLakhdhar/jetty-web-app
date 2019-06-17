#!/bin/bash

echo '  _____         _  _                     '
echo ' |_   _|       (_)| |                    '
echo '   | |   _ __   _ | |_  ___  _ __ __   __'
echo '   | |  |  _ \ | || __|/ _ \|  _ \\ \ / /'
echo '  _| |_ | | | || || |_|  __/| | | |\ V / '
echo ' |_____||_| |_||_| \__|\___||_| |_| \_/  '
echo '                                         '
echo '                                         '


JAVA_VERSION=`java -version 2>&1 |awk 'NR==1{ gsub(/"/,""); print $3 }'`
# export JAVA_VERSION
echo java version "$JAVA_VERSION"
mvn -version
#export MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n"

alias run="mvn jetty:run"
alias debug="mvnDebug jetty:run"