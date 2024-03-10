package tw.pers.jwt.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
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
        String token=request.getHeader("Authorization");
        if(StringUtils.hasText(token)){
            System.out.println(token);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
