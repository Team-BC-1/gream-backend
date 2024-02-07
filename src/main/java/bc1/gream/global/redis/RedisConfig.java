package bc1.gream.global.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.ReadFrom;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

@Configuration
@EnableCaching
public class RedisConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${spring.data.redis.primary.host}")
    private String primaryHost;
    @Value("${spring.data.redis.replica.host}")
    private String replicaHost;
    @Value("${spring.data.redis.port}")
    private Integer port;

    @PostConstruct
    void setup() {
        objectMapper.registerModule(new JavaTimeModule()); // LocalDateTime 파싱
        objectMapper.activateDefaultTyping(typeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
    }

    private PolymorphicTypeValidator typeValidator() {
        return BasicPolymorphicTypeValidator
            .builder()
            .allowIfSubType(Object.class)
            .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // 복제본이 없는 환경에서 실행 시
        if (!StringUtils.hasText(replicaHost)) {
            return new LettuceConnectionFactory(primaryHost, port);
        }

        // ElastiCache 이용하는 배포 환경에서 실행 시
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
            .readFrom(ReadFrom.REPLICA_PREFERRED) // Replica 우선 조회, 장애 시 Primary 조회
            .build();

        RedisStaticMasterReplicaConfiguration serverConfig = new RedisStaticMasterReplicaConfiguration(primaryHost, port);
        serverConfig.addNode(replicaHost, port); // Primary 노드에 Replica 노드 추가

        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
        return stringRedisTemplate;
    }

    @Bean
    public RedisCacheManager cacheManager() {

        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig() // 기본 설정
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // 키는 문자열
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer)) // 직렬화
            .entryTtl(Duration.ofSeconds(5L)) // 기본 캐싱 TTL 설정
            .disableCachingNullValues(); // null 캐싱 불가

        return RedisCacheManager.builder(redisConnectionFactory())
            .cacheDefaults(redisCacheConfiguration)
            .build();
    }
}
