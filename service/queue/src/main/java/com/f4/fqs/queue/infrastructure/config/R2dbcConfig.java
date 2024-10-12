package com.f4.fqs.queue.infrastructure.config;

import com.f4.fqs.queue.domain.model.eventStore.EventTypeToStringConverter;
import com.f4.fqs.queue.domain.model.eventStore.StringToEventTypeConverter;
import com.thoughtworks.xstream.converters.enums.EnumToStringConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;

import java.io.Serializable;
import java.lang.constant.Constable;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@EnableR2dbcAuditing
@EnableR2dbcRepositories
@Configuration
public class R2dbcConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final DatabaseClient databaseClient;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        databaseClient.sql("SELECT 1").fetch().one()
                .subscribe(
                        success -> {
                            log.info("Initialize r2dbc database connection.");
                        },
                        error -> {
                            log.error("Failed to initialize r2dbc database connection.");
                            SpringApplication.exit(event.getApplicationContext(), () -> -110);
                        }
                );
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        return new R2dbcCustomConversions(R2dbcCustomConversions.STORE_CONVERSIONS, Arrays.asList(
                        new EventTypeToStringConverter(),
                        new StringToEventTypeConverter()
                ));
    }

}
