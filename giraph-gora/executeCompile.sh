#! /bin/bash
mvn clean compile package -DskipTests
cp conf/gora-cassandra-mapping.xml target/.
cp conf/gora.properties target/.
