package tw.pers.jwt.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tw.pers.jwt.demo.dto.UserLoginDto;
import tw.pers.jwt.demo.entity.UserDetail;
import tw.pers.jwt.demo.service.UserDetailService;

@RequiredArgsConstructor
@RequestMapping("user")
@RestController
public class UserDetailController{
    private final UserDetailService userDetailService;

    @PostMapping("/login")
    public String userLogin(@RequestBody @Valid UserLoginDto userLoginDto){
        return userDetailService.userSignIn(userLoginDto);
    }
    
    @GetMapping("/{username}")
    public ResponseEntity<?> findUserByUsername(@PathVariable("username") String username){
        if(StringUtils.hasText(username)){
            UserDetail user=userDetailService.userByUsername(username);
            if(user!=null){
                return ResponseEntity.ok().body(user);
            }
        }
        return ResponseEntity.notFound().build();
    }

}
