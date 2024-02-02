package bc1.gream.domain.sell.scheduler;

import bc1.gream.domain.sell.service.command.SellCommandService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SellDeadlineScheduler {

    private final static String BATCH_TIME = "59 59 23 * * ?";
    private final static String REDIS_CLOSE_LOCK = "CLOSE_BUY_SCHEDULE_LOCK";
    private final static String REDIS_CLOSE_LOCK_VALUE = "LOCKED";
    private final static int REDIS_DISTRIBUTED_LOCK_TIMEOUT = 2;

    private final SellCommandService sellCommandService;
    private final StringRedisTemplate redisTemplate;

    @Retryable(
        retryFor = RuntimeException.class,
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    @Scheduled(cron = BATCH_TIME)
    public void closeBuyOfPassedDealine() {
        Boolean hasLock = redisTemplate.opsForValue()
            .setIfAbsent(REDIS_CLOSE_LOCK, REDIS_CLOSE_LOCK_VALUE, REDIS_DISTRIBUTED_LOCK_TIMEOUT, TimeUnit.SECONDS);
        if (hasLock) {
            sellCommandService.deleteSellsOfDeadlineBefore(LocalDateTime.now());
            redisTemplate.delete(REDIS_CLOSE_LOCK);
        }
    }

    @Recover
    void recover(RuntimeException exception) {
        log.error("Exception Message : " + exception.getMessage());
        log.error("Exception Stack Trace : " + Arrays.toString(exception.getCause().getStackTrace()));
    }
}
