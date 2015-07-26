docker run -d -p 9999:9999 \
-e "SPRING_DATA_MONGODB_HOST=172.17.42.1"  \
-e "EUREKA_CLIENT_SERVICE_URL_DEFAULT_ZONE= http://172.17.0.64:8761/eureka/" \
--name auth_service \
auth_service
