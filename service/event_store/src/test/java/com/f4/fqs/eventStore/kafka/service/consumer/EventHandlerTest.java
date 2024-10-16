package com.f4.fqs.eventStore.kafka.service.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EventHandlerTest {

    @Autowired private EventHandler eventHandler;

    String SERVICE_NAME = "ffff";

    /*@Test
    public void 메세지_수동_소비_테스트() throws Exception {


        //when
        eventHandler.(SERVICE_NAME, 1);
        executor.send(SERVICE_NAME, String.valueOf(4489));
        e.consume(SERVICE_NAME, 1);

        executor.send(SERVICE_NAME, String.valueOf(7554));

        e.consume(SERVICE_NAME, 1);
        e.consume(SERVICE_NAME, 1);
        //then

    }*/


}