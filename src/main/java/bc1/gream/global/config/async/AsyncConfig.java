package bc1.gream.global.config.async;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {

    private final GlobalAsyncExceptionHandler globalAsyncExceptionHandler;

    @Override
    @Bean(name = "tossPaymentsExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int numOfCores = Runtime.getRuntime().availableProcessors();
        float targetCpuUtilization = 0.3f;
        float blockingCoefficient = 0.1f;
        int corePoolSize = (int) (numOfCores * targetCpuUtilization * (1 + blockingCoefficient));
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setMaxPoolSize(Integer.MAX_VALUE);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setAllowCoreThreadTimeOut(true);
        executor.setThreadNamePrefix("custom-async-tosspay"); // Set the thread name prefix
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return globalAsyncExceptionHandler;
    }
}