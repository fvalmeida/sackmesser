package org.sackmesser.config;

import lombok.extern.slf4j.Slf4j;
import org.sackmesser.async.ExceptionHandlingAsyncTaskExecutor;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfiguration implements AsyncConfigurer, EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "async.");
    }

    @Override
    @Bean
    public Executor getAsyncExecutor() {
        log.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(propertyResolver.getProperty("corePoolSize", Integer.class, 2));
        executor.setMaxPoolSize(propertyResolver.getProperty("maxPoolSize", Integer.class, 50));
        executor.setQueueCapacity(propertyResolver.getProperty("queueCapacity", Integer.class, 10000));
        executor.setThreadNamePrefix("sackmesser-Executor-");
        return new ExceptionHandlingAsyncTaskExecutor(executor);
    }
}
