package tw.pers.jwt.demo.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.TestPropertySource;
import tw.pers.jwt.demo.domain.UserBean;

import java.util.Optional;

@SpringBootTest
public class UserRepositoryTests{

    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void existsByUserNameTest(){
        System.out.println(userRepository.existsByUsername("Alex"));
    }


    @Test
    public void pwdConvert(){
        String aaa=BCrypt.hashpw("aaa",BCrypt.gensalt());
        UserBean alex=userRepository.findByUsername("Alex").orElse(null);
        alex.setBcryptpwd(aaa);
        userRepository.save(alex);
    }

}
