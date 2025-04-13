package tw.pers.jwt.demo.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import tw.pers.jwt.demo.SystemConstants;
import tw.pers.jwt.demo.converter.UserDtoConverter;
import tw.pers.jwt.demo.dao.UserDetailRepository;
import tw.pers.jwt.demo.dto.UserLoginDto;
import tw.pers.jwt.demo.entity.UserDetail;
import tw.pers.jwt.demo.exception.ErrorCode;
import tw.pers.jwt.demo.exception.LoginFailureException;
import tw.pers.jwt.demo.util.HttpUtils;
import tw.pers.jwt.demo.util.JwtUtils;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;
    private final RedisService redisService;
    private final UserDtoConverter converter;

    /**
     * 登入用，用帳號密碼去資料庫找會員資料，帳密正確回傳JWT
     *
     * @param userLoginDto login Info
     * @return String--JWT
     */
    public void userSignIn(UserLoginDto userLoginDto, HttpServletResponse response) {
        UserDetail loginInfo = converter.to(userLoginDto);
        //檢查帳號名稱是否存在
        if (userDetailRepository.existsByUsername(loginInfo.getUsername())) {
            UserDetail userDetail = userDetailRepository.findByUsername(loginInfo.getUsername());

            if (BCrypt.checkpw(loginInfo.getPassword().getBytes(), userDetail.getBcryptpwd())) {
                String token = JwtUtils.generateToken(userDetail);
                String userLoginUUID = UUID.randomUUID().toString();
                redisService.setValue(userLoginUUID, token);
                HttpUtils.setCookie(response, SystemConstants.USER_LOGIN_UUID, userLoginUUID);
            }
        } else {
            throw new LoginFailureException("User not exit", ErrorCode.LOGIN_FAILURE);
        }
    }

    /**
     * 用會員名字(Username)去找會員資料
     *
     * @param username--String--會員名字
     * @return UserBean--會員資料
     */
    public UserDetail userByUsername(String username) {
        return userDetailRepository.findByUsername(username);
    }
}
