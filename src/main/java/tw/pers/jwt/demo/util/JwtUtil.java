package tw.pers.jwt.demo.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import tw.pers.jwt.demo.entity.UserDetail;

import java.security.Key;
import java.util.Date;

@Slf4j
@UtilityClass
public class JwtUtil{
    private final static String SECRET_KEY = "443185454aa1d224ae5fa6a04847d7c690abe7eac0e7b97e5fd885598ef197a0";
    
    /**
     * 用來生成token
     */
    public String generateToken(UserDetail userDetail) {
        return Jwts.builder()
                .claim("userId", userDetail.getUserId())
                .claim("permission",userDetail.getPermission())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+10*1000))
                .signWith(getSignKey())
                .compact();
    }

    /**
     * 驗證token是否有效
     */
    public boolean isValid(String token){
        boolean result = false;
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parse(token);
            result= true;
        } catch(MalformedJwtException e){
            log.error("Invalid JWT token: {}",e.getMessage());
        } catch(ExpiredJwtException e){
            log.error("JWT token is expired: {}",e.getMessage());
        } catch(UnsupportedJwtException e){
            log.error("JWT token is unsupported: {}",e.getMessage());
        } catch(IllegalArgumentException e){
            log.error("JWT claims string is empty: {}",e.getMessage());
        }
        return result;

    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
