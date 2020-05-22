PROJECT_NAME := server

build:
	./gradlew clean
	./gradlew build

docker-build: build
	docker build -t $(PROJECT_NAME) .

heroku: docker-build
ifndef login
	heroku container:login
endif
	heroku container:push web --app=aiposizi-$(PROJECT_NAME)
	heroku container:release web --app=aiposizi-$(PROJECT_NAME)
	heroku stack:set heroku-18 --app=aiposizi-$(PROJECT_NAME)