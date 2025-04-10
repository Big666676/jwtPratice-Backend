package tw.pers.jwt.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.pers.jwt.demo.entity.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail,Integer>{
    boolean existsByUsername(String username);
    
    UserDetail findByUsername(String username);
}
