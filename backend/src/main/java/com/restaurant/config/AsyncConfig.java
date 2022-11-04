package com.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

// any async method that returns an object should return "CompletableFuture", similar to js promise
// return CompletableFuture.completedFuture(customer)
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "asyncExecutor1") // add name when there is more than one task executor bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);  // pool qty available at all times
        executor.setMaxPoolSize(10);  // max pools that can exist
        executor.setQueueCapacity(50);  // queue size per thread
        executor.setThreadNamePrefix("AsyncThread::"); // prefix name to apply to the thread
        executor.initialize();  // start / initialize this task executor
        return executor;
    }
}
