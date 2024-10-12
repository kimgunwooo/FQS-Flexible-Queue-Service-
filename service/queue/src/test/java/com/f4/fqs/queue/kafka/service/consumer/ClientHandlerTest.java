package com.f4.fqs.queue.kafka.service.consumer;

import com.f4.fqs.queue.kafka.service.producer.ClientQueueExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClientHandlerTest {

    @Autowired private ClientQueueExecutor executor;
    @Autowired private ClientHandler clientHandler;

    String SERVICE_NAME = "ffff";

    @Test
    public void 메세지_수동_소비_테스트() throws Exception {

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