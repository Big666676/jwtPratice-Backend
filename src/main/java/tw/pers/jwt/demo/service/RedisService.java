package tw.pers.jwt.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisService{
    
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 使用StringRedisTemplate
     * 輸入key取得value
     * @param key
     * @return String--value
     */
    public String getValue(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
    /**
     * 使用StringRedisTemplate
     * 輸入key跟value存入redis
     * @param key
     * @param value
     */
    public void setValue(String key,String value){
        stringRedisTemplate.opsForValue().set(key, value);
    }


}
