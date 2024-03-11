package tw.pers.jwt.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tw.pers.jwt.demo.util.JwtUtil;

import java.io.IOException;
@WebFilter(urlPatterns="/user/check")
@Component
public class JwtFilter implements Filter{
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse,FilterChain filterChain) throws IOException, ServletException{
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token=request.getHeader("Authorization");
        //檢查是否有token
        if(StringUtils.hasText(token)){
            //檢查token是否還有效
            if(!jwtUtil.isVaild(token.substring(7).trim())){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Resource not found");
                return;
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
