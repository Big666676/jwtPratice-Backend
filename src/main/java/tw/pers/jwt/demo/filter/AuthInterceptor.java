package tw.pers.jwt.demo.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tw.pers.jwt.demo.service.AuthService;

/**
 * 用於檢查是否登入的攔截器
 */
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor{

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
        return authService.isValid(request);
    }
}
