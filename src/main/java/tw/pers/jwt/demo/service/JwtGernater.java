package tw.pers.jwt.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.pers.jwt.demo.domain.UserBean;

import java.security.Key;
import java.util.Date;

@Service
@Transactional
public class JwtGernater {
    private final static String SECRET_KEY = "443185454aa1d224ae5fa6a04847d7c690abe7eac0e7b97e5fd885598ef197a0";


    //用來生成token
    public String generateToken(UserBean userBean) {
        return Jwts.builder()
                .claim("userId", userBean.getUserId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+30*60*1000))
                .signWith(getSignKey())
                .compact();
    }


    public boolean isVaild(String token) {
           Jwts.parser()
                  .verifyWith(getSignKey())
                  .build()
                  .parse(token);
//          System.out.println(payload);
       return true;
      }


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
