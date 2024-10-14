//package com.f4.fqs.gateway.config;
// 당장 사용 안 해서 일단 주석 처리
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//import org.springframework.data.redis.cache.CacheKeyPrefix;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//@Configuration
//@EnableCaching
//public class CacheConfig {
//    @Bean
//    public RedisCacheManager cacheManager(
//            RedisConnectionFactory connectionFactory
//    ) {
//        RedisCacheConfiguration config = RedisCacheConfiguration
//                .defaultCacheConfig()
//                .disableCachingNullValues()
//                .entryTtl(Duration.ofMinutes(60L))
//                .computePrefixWith(CacheKeyPrefix.simple())
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json())
//                );
//
//        return RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(config)
//                .build();
//    }
//}
