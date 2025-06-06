package tw.pers.jwt.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "user_detail")
public class UserDetail{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "username",columnDefinition = "nvarchar")
    private String username;
    @Column(name = "password",columnDefinition = "varchar")
    private String password;
    @Column(name = "email",columnDefinition = "nvarchar")
    private String email;
    @Column(name = "birth")
    private Date birth;
    @Column(name = "permission")
    private Integer permission;
    @Column(name = "bcryptpwd",columnDefinition = "varchar")
    private String bcryptpwd;
    @Column(name = "enabled")
    private Integer enabled;


}
