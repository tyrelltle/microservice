docker run -d -p 3000:3000 \
-e SPRING_DATA_MONGODB_HOST=172.17.42.1  \
-e "EUREKA_CLIENT_SERVICE_URL_DEFAULT_ZONE=http://172.17.0.64:8761/eureka/" \
-e "EUREKA_INSTANCE_PREFER_IP_ADDRESS=true" \
--name inventory_service \
inventory_service
