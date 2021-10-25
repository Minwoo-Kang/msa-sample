set -x
# product
CONTAINER_NAME=product-db
DATABASE_NAME=product
PORT=13306
docker run --name ${CONTAINER_NAME} -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=coupang -e MYSQL_PASSWORD=coupang -e MYSQL_DATABASE=${DATABASE_NAME} -p ${PORT}:3306 -d mysql:8.0
# review
CONTAINER_NAME=review-db
DATABASE_NAME=review
PORT=23306
docker run --name ${CONTAINER_NAME} -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=coupang -e MYSQL_PASSWORD=coupang -e MYSQL_DATABASE=${DATABASE_NAME} -p ${PORT}:3306 -d mysql:8.0