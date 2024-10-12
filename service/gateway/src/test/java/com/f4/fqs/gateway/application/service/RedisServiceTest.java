package com.f4.fqs.gateway.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void 줄세우기_테스트() throws Exception {

        IntStream.range(0, 10000)
                .boxed()
                .forEach(i -> {
                    CompletableFuture.runAsync(() -> redisService.lineUp(String.valueOf(i)));
                });

        long myRank = redisService.getMyRank("6663");
        System.out.println(myRank);

        //given
        
        //when
        
        //then
    
    }
    
}