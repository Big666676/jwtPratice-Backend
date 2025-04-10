package tw.pers.jwt.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import tw.pers.jwt.demo.converter.UserDtoConverter;
import tw.pers.jwt.demo.dao.UserDetailRepository;
import tw.pers.jwt.demo.dto.UserLoginDto;
import tw.pers.jwt.demo.entity.UserDetail;
import tw.pers.jwt.demo.util.JwtUtil;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailService{
    
    private final UserDetailRepository userDetailRepository;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;
    private final UserDtoConverter converter;

    /**
     * 登入用，用帳號密碼去資料庫找會員資料，帳密正確回傳JWT
     *
     * @param userLoginDto login Info
     * @return String--JWT
     */
    public String userSignIn(UserLoginDto userLoginDto) {
        String result = null;
        UserDetail loginInfo=converter.to(userLoginDto);
        //檢查帳號名稱是否存在
        if (userDetailRepository.existsByUsername(loginInfo.getUsername())) {
            UserDetail userDetail = userDetailRepository.findByUsername(loginInfo.getUsername());
                //密碼正確才產生token
                if (BCrypt.checkpw(loginInfo.getPassword().getBytes(), userDetail.getBcryptpwd())) {
                    //產生token
                    result = JwtUtil.generateToken(userDetail);
            }
        }
       
        return result;
    }

    /**
     * 用會員名字(Username)去找會員資料，配合redis，假如redis已有資料直接從redis抓取資料
     *
     * @param username--String--會員名字
     * @return UserBean--會員資料
     */
    public UserDetail userByUsername(String username) {
        UserDetail user = null;
        try {
            String redTest1 = redisService.getValue(username);
            if (redTest1 == null) {
                user = userDetailRepository.findByUsername(username);
                if (user != null) {
                    redisService.setValue(user.getUsername(), objectMapper.writeValueAsString(user));
                    redTest1 = redisService.getValue(user.getUsername());
                }
            }
            user = objectMapper.readValue(redTest1, UserDetail.class);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException {}",e.getMessage());
        }
        return user;
    }
}
