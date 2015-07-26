docker run -d -p 8761:8761 \
-e "SPRING_DATA_MONGODB_HOST=172.17.42.1"  \
--name discovery_service \
discovery_service
