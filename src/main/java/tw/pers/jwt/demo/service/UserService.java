package tw.pers.jwt.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tw.pers.jwt.demo.dao.UserRepository;
import tw.pers.jwt.demo.domain.UserBean;
import tw.pers.jwt.demo.util.JwtUtil;
import tw.pers.jwt.demo.util.RedisUtil;

@Service
@Transactional(rollbackFor = {Exception.class})
public class UserService {
    private final static Logger logger=LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 登入用，用帳號密碼去資料庫找會員資料，帳密正確回傳JWT
     *
     * @param request--UserBean
     * @return String--登入成功回傳JWT
     */
    public String userSignIn(UserBean request) {
        String username = request.getUsername();
        String requestpwd = request.getPassword();
        //檢查是否有回傳帳號及密碼
        if (!StringUtils.hasText(username) && !StringUtils.hasText(requestpwd)) {
            return null;
        }
        //檢查帳號名稱是否存在
        if (!userRepository.existsByUsername(username)) {
            return null;
        }
        UserBean user = userRepository.findByUsername(username).orElse(null);
        //如果user為不等於null
        if (user != null) {
            boolean checkpwd = BCrypt.checkpw(requestpwd.getBytes(), user.getBcryptpwd());
            //密碼正確才產生token
            if (checkpwd) {
                //產生token
                String token = jwtUtil.generateToken(user);
                return token;
            }
        }
        return null;
    }

    /**
     * 用會員名字(Username)去找會員資料，配合redis，假如redis已有資料直接從redis抓取資料
     *
     * @param username--String--會員名字
     * @return UserBean--會員資料
     */
    public UserBean userByUsername(String username) {
        UserBean user = null;
        try {
            String redTest1 = redisUtil.getValue(username);
            System.out.println(1);
            if (redTest1 == null) {
                user = userRepository.findByUsername(username).orElse(null);
                System.out.println(2);
                if (user != null) {
                    redisUtil.setValue(user.getUsername(), objectMapper.writeValueAsString(user));
                    redTest1 = redisUtil.getValue(user.getUsername());
                }
            }
            user = objectMapper.readValue(redTest1, UserBean.class);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException {}",e.getMessage());
        }
        return user;
    }
}
