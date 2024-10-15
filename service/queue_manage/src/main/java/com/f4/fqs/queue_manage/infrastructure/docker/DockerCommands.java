package com.f4.fqs.queue_manage.infrastructure.docker;

public interface DockerCommands {
    String REDIS_RUN = "docker run -d --name %s_redis --network ec2-user_fqs_network -p %d:6379 redis:latest";
    String SPRING_RUN = "docker run -d --name %s_queue_server --network ec2-user_fqs_network -p %d:8080 " +
            "-e SERVICE_NAME=%s " +
            "-e REDIS_HOST=%s_redis %s";
    String EXEC_INSTALL_DOCKER = "docker exec %s_queue_server amazon-linux-extras install docker -y";

    String REDIS_STOP = "docker stop %s_redis";
    String REDIS_REMOVE = "docker rm %s_redis";
    String SPRING_STOP = "docker stop %s_queue_server";
    String SPRING_REMOVE = "docker rm %s_queue_server";
}

