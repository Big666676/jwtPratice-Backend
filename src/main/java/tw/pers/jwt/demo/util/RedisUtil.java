package tw.pers.jwt.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RedisUtil{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
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
