package bc1.gream.global.config;

import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

    @Override
    @Bean(name = "tossPaymentsExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1); // Set the core pool size. Default is 1.
        executor.setMaxPoolSize(10); // Set the maximum pool size. Default is  Integer.MAX_VALUE.
        executor.setQueueCapacity(10); // Set the capacity of the task queue. Default is Integer.MAX_VALUE.
        executor.setThreadNamePrefix("custom-async-tosspay"); // Set the thread name prefix
        executor.setRejectedExecutionHandler((r, executor1) ->
            log.error("Task rejected : {} , thread pool is full and queue is also full : {}",
                r.toString(),
                executor1.getCorePoolSize()));
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) ->
            log.error("Exception handler for async method : {} throw unexpected exception {}",
                method.toGenericString(), ex);
    }
}