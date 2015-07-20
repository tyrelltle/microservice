#!/usr/bin/env bash

cd microservices/api/product-api-service; sudo ./gradlew distDocker; cd -
cd microservices/support/auth-server; sudo ./gradlew distDocker; cd -
cd microservices/support/discovery-server; sudo ./gradlew distDocker; cd -
cd microservices/support/edge-server; sudo ./gradlew distDocker; cd -
