#! /bin/bash -e

rm -fr build
mkdir build
cp ../build/libs/auth-server-0.0.1-SNAPSHOT.jar build

docker build -t auth_service .
