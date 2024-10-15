package com.f4.fqs.queue_manage.infrastructure.docker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;

@Slf4j
@Service
public class DockerService {

    /**
     *  TODO. Redis 와 Spring 서버 중 하나만 띄워진다면? 둘 다 안띄워진다면?
     *  기본적으로 비동기로 동작하기 때문에 시스템이 정확하게 띄워졌는지 확인할 길이 없음. polling 을 통해 해결?
     */
    public void runServices(String name, int springPort, int redisPort, String springImage) {
        String[] commands = {
                String.format(DockerCommands.REDIS_RUN, name, redisPort),
                String.format(DockerCommands.SPRING_RUN, name, springPort, name, name, springImage),
                String.format(DockerCommands.EXEC_INSTALL_DOCKER, name)
        };

        for (String command : commands) {
            executeCommand(command);
        }
    }

    public void stopServices(String name) {
        String[] commands = {
                String.format(DockerCommands.REDIS_STOP, name),
                String.format(DockerCommands.REDIS_REMOVE, name),
                String.format(DockerCommands.SPRING_STOP, name),
                String.format(DockerCommands.SPRING_REMOVE, name)
        };

        for (String command : commands) {
            executeCommand(command);
        }
    }

    private void executeCommand(String command) {
        int exitCode = -1;  // 기본값 설정
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", command);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    log.info(line);
                }

                while ((line = errorReader.readLine()) != null) {
                    log.error(line);
                }

                exitCode = process.waitFor();
                log.info("Exited with code: " + exitCode);
            }
        } catch (Exception e) {
            log.error("Error executing command '{}': {}", command, e.getMessage(), e);
        }
    }


}
