package com.f4.fqs.eventStore.kafka.service.consumer;

import com.f4.fqs.eventStore.kafka.consumer.EventHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class EventHandlerTest {

    @Autowired private EventHandler eventHandler;

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

    @Test
    public void 카프카_스트림_테스트() throws Exception {

        eventHandler.replay(
                "aspa-2030-winter",
                LocalDateTime.of(2024, 10, 20, 15, 20, 0),
                LocalDateTime.of(2024, 10, 20, 15, 30, 0)
        )
        .subscribe(
                System.out::println,
                e -> System.out.println("e = " + e),
                () -> System.out.println("eventHandler = " + eventHandler)
        );


        /*.toStream()
                .toList().forEach(System.out::println)*/

        ;
        //given

        //when
        
        //then
    
    }
    
    
}