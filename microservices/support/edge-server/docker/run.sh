docker run -d -p 8765:8765 \
-e SPRING_DATA_MONGODB_HOST=172.17.42.1  \
-e "EUREKA_CLIENT_SERVICE_URL_DEFAULT_ZONE=http://172.17.0.64:8761/eureka/" \
--name edge_service \
edge_service
