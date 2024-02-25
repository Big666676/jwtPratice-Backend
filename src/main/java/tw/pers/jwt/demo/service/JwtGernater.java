package tw.pers.jwt.demo.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.pers.jwt.demo.domain.UserBean;

import java.security.Key;
import java.util.Date;

@Service
@Transactional
public class JwtGernater {
    private final static String SECRET_KEY = "443185454aa1d224ae5fa6a04847d7c690abe7eac0e7b97e5fd885598ef197a0";

    private final static Logger logger=LoggerFactory.getLogger(JwtGernater.class);
    //
    //用來生成token
    public String generateToken(UserBean userBean) {
        return Jwts.builder()
                .claim("userId", userBean.getUsername())
                .setSubject(userBean.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+30*60*1000))
                .signWith(getSignKey())
                .compact();
    }


   

    //驗證token是否有效
    public boolean isVaild(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parse(token);
            return true;
        } catch(MalformedJwtException e){
            logger.error("Invalid JWT token: {}",e.getMessage());
        } catch(ExpiredJwtException e){
            logger.error("JWT token is expired: {}",e.getMessage());
        } catch(UnsupportedJwtException e){
            logger.error("JWT token is unsupported: {}",e.getMessage());
        } catch(IllegalArgumentException e){
            logger.error("JWT claims string is empty: {}",e.getMessage());
        }
        return false;

    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
