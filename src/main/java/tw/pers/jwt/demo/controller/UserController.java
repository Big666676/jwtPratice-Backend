package tw.pers.jwt.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.pers.jwt.demo.domain.UserBean;
import tw.pers.jwt.demo.service.JwtGernater;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private JwtGernater jwtGernater;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserBean request){
        System.out.println(request);
        String s = jwtGernater.generateToken(request);
        return ResponseEntity.ok().body(s);

    }

    @PostMapping("/check")
    public ResponseEntity<?> checkValid(@RequestHeader("Authorization") String asd){
//        System.out.println(userBean);
        System.out.println(asd);
        String jwtToken=asd.substring(7).trim();
        System.out.println(jwtToken);
        System.out.println(jwtGernater.isVaild(asd));
        return ResponseEntity.ok().body("SSS");

    }

}
