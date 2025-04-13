package tw.pers.jwt.demo.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tw.pers.jwt.demo.SystemConstants;
import tw.pers.jwt.demo.exception.CustomException;
import tw.pers.jwt.demo.exception.ErrorCode;
import tw.pers.jwt.demo.util.HttpUtils;
import tw.pers.jwt.demo.util.JwtUtils;

@RequiredArgsConstructor
@Service
public class AuthService{

    private final RedisService redisService;
    
    public boolean isValid(HttpServletRequest request){
        String userLoginUUID =HttpUtils.getCookie(request,SystemConstants.USER_LOGIN_UUID);
        if(StringUtils.isEmpty(userLoginUUID)){
            throw new CustomException("cookie not found", HttpStatus.UNAUTHORIZED, ErrorCode.USER_NOTLOGIN);
        }
        
        String token=redisService.getValue(userLoginUUID);
        return JwtUtils.isValid(token.substring(7).trim());
    }
}
