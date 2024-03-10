package tw.pers.jwt.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.pers.jwt.demo.domain.UserBean;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserBean,Integer>{
    boolean existsByUsername(String username);
    
    Optional<UserBean> findByUsername(String username);
}
