package tw.pers.jwt.demo.redisfortry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import tw.pers.jwt.demo.dao.UserRepository;
import tw.pers.jwt.demo.domain.UserBean;

import java.util.Optional;

@SpringBootTest
public class RedTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void redTest1() {
        ValueOperations<String, String> vos = redisTemplate.opsForValue();
        vos.set("qqqqTest1", "Test");
        String redTest1 = vos.get("qqqqTest1");
        System.out.println(redTest1);
    }

    @Test
    public void redTest2() {
        ValueOperations<String, UserBean> vos = redisTemplate.opsForValue();
        UserBean redTest1 = vos.get("Babe");
        if (redTest1 == null) {
            UserBean user = userRepository.findById(1).orElse(null);
            if (user != null) {
                vos.set(user.getUsername(), user);
                redTest1=vos.get(user.getUsername());
                System.out.println(123);
            }
        }
        System.out.println(redTest1);
    }


}
