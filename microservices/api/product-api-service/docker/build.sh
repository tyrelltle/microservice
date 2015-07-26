#! /bin/bash -e

rm -fr build
mkdir build
cp ../build/libs/product-api-service-0.0.1-SNAPSHOT.jar build

docker build -t inventory_service .
