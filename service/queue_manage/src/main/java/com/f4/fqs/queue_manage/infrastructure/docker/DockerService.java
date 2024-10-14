package com.f4.fqs.queue_manage.infrastructure.docker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class DockerService {

//    @Value("classpath:docker-compose.yml")
//    private String composeFilePath; // 리소스 파일 경로

    /**
     *  TODO. Redis 와 Spring 서버 중 하나만 띄워진다면? 둘 다 안띄워진다면?
     *  기본적으로 비동기로 동작하기 때문에 시스템이 정확하게 띄워졌는지 확인할 길이 없음. polling 을 통해 해결?
     */
    public void runServices(String name, int springPort, int redisPort, String springImage) {
        // TODO. 현재 mac 로컬환경에서는 sudo 명령에 따른 시스템 비밀번호 입력이 필요하기에 다음과 같이 정의
        String redisCommand = String.format(
                "echo 1541 | sudo -S docker run -d --name %s_redis -p %d:6379 redis:latest",
                name, redisPort
        );

        String springCommand = String.format(
                "echo 1541 | sudo -S docker run -d --name %s_queue_server -p %d:8080 " +
                        "-e SERVICE_NAME=%s " +
                        "-e REDIS_HOST=%s_redis " +
                        "%s",
                name, springPort, name, name, springImage
        );

        executeCommand(redisCommand);
        executeCommand(springCommand);
    }

    public void stopServices(String name) {
        String redisStopCommand = String.format(
                "echo 1541 | sudo -S docker stop %s_redis",
                name
        );

        String redisRemoveCommand = String.format(
                "echo 1541 | sudo -S docker rm %s_redis",
                name
        );

        String springStopCommand = String.format(
                "echo 1541 | sudo -S docker stop %s_queue_server",
                name
        );

        String springRemoveCommand = String.format(
                "echo 1541 | sudo -S docker rm %s_queue_server",
                name
        );

        executeCommand(redisStopCommand);
        executeCommand(redisRemoveCommand);
        executeCommand(springStopCommand);
        executeCommand(springRemoveCommand);
    }


    private void executeCommand(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", command);
            Process process = processBuilder.start();

            // 출력 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }

            // 에러 출력 읽기
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                log.error(line);
            }

            int exitCode = process.waitFor();
            log.info("Exited with code: " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error executing command: {}", e.getMessage());
        }
    }

}
