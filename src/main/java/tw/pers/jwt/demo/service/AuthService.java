package tw.pers.jwt.demo.service;

import org.springframework.stereotype.Service;
import tw.pers.jwt.demo.util.JwtUtil;

@Service
public class AuthService{
    
    public boolean isValid(String token){
        return JwtUtil.isValid(token.substring(7).trim());
    }
}
