package com.f4.fqs.queue.kafka.service.consumer;

import com.f4.fqs.queue.kafka.service.producer.ClientQueueExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scala.util.control.TailCalls;
import software.amazon.awssdk.awscore.AwsClient;
import software.amazon.awssdk.services.ecr.EcrAsyncClient;
import software.amazon.awssdk.services.ecr.EcrClient;
import software.amazon.awssdk.services.ecs.EcsAsyncClient;
import software.amazon.awssdk.services.ecs.model.*;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
class ClientHandlerTest {

    @Autowired private ClientQueueExecutor executor;
    @Autowired private ClientHandler clientHandler;

    @Autowired private EcrClient ecrClient;

    @Autowired private EcrAsyncClient ecrAsyncClient;
    @Autowired private EcsAsyncClient ecsAsyncClient;

    String SERVICE_NAME = "ffff";

    @Test
    public void 메세지_수동_소비_테스트() throws Exception {

        RegisterTaskDefinitionRequest registerRequest = RegisterTaskDefinitionRequest.builder()
                .family()
                .containerDefinitions(ContainerDefinition.builder()
                        .name()
                        .image()
                        .essential()
                        .environment()
                        .portMappings(PortMapping.builder()
                                .containerPort()
                                .hostPort()
                                .protocol(TransportProtocol.TCP)
                                .build())
                .build())
                .build();

        RegisterTaskDefinitionResponse response = ecsAsyncClient.registerTaskDefinition(registerRequest).get();

        RunTaskRequest runTaskRequest = RunTaskRequest.builder()
                .cluster()
                .taskDefinition(response.taskDefinition())
                .count(1)
                .build();

        RunTaskResponse runTaskResponse = ecsAsyncClient.runTask(runTaskRequest).get();

        runTaskResponse.tasks().get(0).taskArn();


        StopTaskRequest request = StopTaskRequest.builder()
                .cluster("cluster")
                .task("task")
                .reason("reason")
                .build();

        ecsAsyncClient.stopTask(request);

        //given
        executor.send(SERVICE_NAME, String.valueOf(6363));
        executor.send(SERVICE_NAME, String.valueOf(2115));
        executor.send(SERVICE_NAME, String.valueOf(9884));

        //when
        clientHandler.consume(SERVICE_NAME, 1);
        executor.send(SERVICE_NAME, String.valueOf(4489));
        clientHandler.consume(SERVICE_NAME, 1);

        executor.send(SERVICE_NAME, String.valueOf(7554));

        clientHandler.consume(SERVICE_NAME, 1);
        clientHandler.consume(SERVICE_NAME, 1);
        //then

    }

}