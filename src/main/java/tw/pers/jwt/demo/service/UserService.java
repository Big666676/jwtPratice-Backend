package tw.pers.jwt.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tw.pers.jwt.demo.dao.UserRepository;
import tw.pers.jwt.demo.domain.UserBean;
import tw.pers.jwt.demo.util.JwtUtil;

@Service
@Transactional(rollbackFor={Exception.class})
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String userSignIn(UserBean request){
        String username=request.getUsername();
        String requestpwd=request.getPassword();
        //檢查是否有回傳帳號及密碼
        if(!StringUtils.hasText(username) && !StringUtils.hasText(requestpwd)){
            return null;
        }
        //檢查帳號名稱是否存在
        if(!userRepository.existsByUsername(username)){
            return null;
        }
        UserBean user=userRepository.findByUsername(username).orElse(null);
        //如果user為不等於null
        if(user!=null){
            boolean checkpwd=BCrypt.checkpw(requestpwd.getBytes(),user.getBcryptpwd());
            //密碼正確才產生token
            if(checkpwd){
                //產生token
                String token=jwtUtil.generateToken(user);
                return token;
            }
        }
        return null;
    }
}
