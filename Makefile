.PHONY: run build test image-and-push

run:


build:


docker:
	sudo docker-compose up -d

image-and-push:
	export $(cat .env | xargs)
	#./gradlew clean :service:eureka:jib
	#./gradlew clean :service:gateway:gateway_server:jib
	#./gradlew clean :service:user:jib
	#./gradlew clean :service:auth:jib
	#./gradlew clean :service:queue_manage:jib
	./gradlew clean :service:queue:jib
	./gradlew clean :service:event_store:jib