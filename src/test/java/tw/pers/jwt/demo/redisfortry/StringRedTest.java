package tw.pers.jwt.demo.redisfortry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import tw.pers.jwt.demo.dao.UserRepository;
import tw.pers.jwt.demo.domain.UserBean;
@SpringBootTest
public class StringRedTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void stringRedTest2() throws JsonProcessingException {
        ValueOperations<String, String> svo = stringRedisTemplate.opsForValue();
        String redTest1 = svo.get("Babe");
        UserBean user=null;
        if(redTest1==null) {
            user = userRepository.findById(2).orElse(null);
            if (user != null) {
                String userString = objectMapper.writeValueAsString(user);
                svo.set(user.getUsername(), userString);
                redTest1=svo.get(user.getUsername());
                System.out.println(123);
            }
        }
        user= objectMapper.readValue(redTest1, UserBean.class);
        System.out.println(user);
    }
}
