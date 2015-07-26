#! /bin/bash -e

rm -fr build
mkdir build
cp ../build/libs/discovery-server-0.0.1-SNAPSHOT.jar build

docker build -t discovery_service .
