package tw.pers.jwt.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tw.pers.jwt.demo.domain.UserBean;
import tw.pers.jwt.demo.service.UserService;
import tw.pers.jwt.demo.util.JwtUtil;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserBean request) {
        if (request != null) {
            String token = userService.userSignIn(request);
            if (StringUtils.hasText(token)) {
                return ResponseEntity.ok().body(token);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkValid(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7).trim();
        if (jwtUtil.isVaild(jwtToken)) {
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findUserByUsername(@PathVariable("username") String username) {
        if (StringUtils.hasText(username)) {
            UserBean user = userService.userByUsername(username);
            if (user != null) {
                return ResponseEntity.ok().body(user);
            }
        }
        return ResponseEntity.notFound().build();
    }

}
