#!/usr/bin/env bash
rabbitmq-server

cd microservices/support/auth-server;                 ./gradlew bootRun; cd -
cd microservices/support/discovery-server;            ./gradlew bootRun; cd -
cd microservices/support/edge-server;                 ./gradlew bootRun; cd -
cd microservices/support/monitor-dashboard;           ./gradlew bootRun; cd -
cd microservices/support/turbine;                     ./gradlew bootRun; cd -


cd microservices/core/product-service;                ./gradlew bootRun; cd -
cd microservices/core/recommendation-service;         ./gradlew bootRun; cd -
cd microservices/core/review-service;                 ./gradlew bootRun; cd -
cd microservices/composite/product-composite-service; ./gradlew bootRun; cd -
cd microservices/api/product-api-service;             ./gradlew bootRun; cd -

