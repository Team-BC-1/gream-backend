package bc1.gream.global.redis;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Redis Util")
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object o, long ms) {
        log.info("[set] key : {}, value : {}, time : {} minutes", key, o, ms);
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
        redisTemplate.opsForValue().set(key, o, ms, TimeUnit.MILLISECONDS);
    }

    public Object get(String key) {
        log.info("[get] key : {}", key);
        Object value = redisTemplate.opsForValue().get(key);
        log.info("[get] value : {}", value);
        return value;
    }

    public boolean delete(String key) {
        log.info("[delete] key {}", key);
        boolean isDelete = Boolean.TRUE.equals(redisTemplate.delete(key));
        log.info("[delete] isDelete : {}", isDelete);
        return isDelete;
    }

    public boolean hasKey(String key) {
        log.info("[hasKey] key : {}", key);
        boolean hasKey = Boolean.TRUE.equals(redisTemplate.hasKey(key));
        log.info("[hasKey] hasKey : {}", hasKey);
        return hasKey;
    }
}