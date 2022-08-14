# Docker Container Stop
docker stop order-write-mysql
docker stop order-read-mysql
#docker stop product-rabbitmq
#docker stop product-mongo
#docker stop product-redis

# Docker Container Remove
docker rm order-write-mysql
docker rm order-read-mysql
#docker rm product-rabbitmq
#docker rm product-mongo
#docker rm product-redis

# Docker Image Remove
docker rmi order-write-mysql
docker rmi order-read-mysql

## shellcheck disable=SC2046
#docker rmi $(docker images --filter=reference="mongo" -q)
## shellcheck disable=SC2046
#docker rmi $(docker images --filter=reference="rabbitmq" -q)
## shellcheck disable=SC2046
#docker rmi $(docker images --filter=reference="redis" -q)

# Docker Newtork Remove
docker network rm order_order_mysql_net
#docker network rm docker_product_mongo_net
#docker network rm docker_product_rabbitmq_net
#docker network rm docker_product_redis_net

# Docker Volume Remove All
docker volume prune -f
