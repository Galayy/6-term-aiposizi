PROJECT_NAME := server
DOCKER_HUB_REPOSITORY := galayy/aiposizi-server

build:
	./gradlew clean
	./gradlew build

docker-build: build
	docker build -t $(PROJECT_NAME) .

docker-push: docker-build
	docker tag ${PROJECT_NAME} ${DOCKER_HUB_REPOSITORY}:latest
	docker push ${DOCKER_HUB_REPOSITORY}:latest

