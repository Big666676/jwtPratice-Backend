package tw.pers.jwt.demo.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import tw.pers.jwt.demo.service.AuthService;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor{

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
        boolean result=false;
        String token=request.getHeader("Authorization");
        //檢查是否有token
        if(StringUtils.hasText(token)){
            //檢查token是否還有效
            result=authService.isValid(token);
        }
        return result;
    }
}
