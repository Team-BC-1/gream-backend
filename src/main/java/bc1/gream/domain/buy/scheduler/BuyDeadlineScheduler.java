package bc1.gream.domain.buy.scheduler;

import bc1.gream.domain.buy.service.BuyService;
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
public class BuyDeadlineScheduler {

    private final BuyService buyService;
    private final StringRedisTemplate redisTemplate;

    @Retryable(
        retryFor = RuntimeException.class,
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    @Scheduled(cron = "59 59 23 * * ?")
    public void closeBuyOfPassedDealine() {
        Boolean hasLock = redisTemplate.opsForValue().setIfAbsent("CLOSE_BUY_SCHEDULE_LOCK", "LOCKED", 2, TimeUnit.SECONDS);
        if (hasLock) {
            buyService.deleteBuysOfDeadlineBefore(LocalDateTime.now());
            redisTemplate.delete("CLOSE_BUY_SCHEDULE_LOCK");
        }
    }

    @Recover
    void recover(RuntimeException exception) {
        log.error("Exception Message : " + exception.getMessage());
        log.error("Exception Stack Trace : " + Arrays.toString(exception.getCause().getStackTrace()));
    }
}
