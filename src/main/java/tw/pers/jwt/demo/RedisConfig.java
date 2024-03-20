package tw.pers.jwt.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

/*
此類別是用來設定資料傳到redis時的序列化方法，如果沒有設置，會用JDK內建的方式，
會讓資料存進redis後看不懂存了什麼

 */
//@Configuration
public class RedisConfig {

//    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //設定JSON的序列化
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //設定Key的序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        //設定Value的序列化
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        //設定HashKey的序列化
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        //設定HashValue的序列化
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        return redisTemplate;

    }

}
