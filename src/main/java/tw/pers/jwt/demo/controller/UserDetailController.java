package tw.pers.jwt.demo.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tw.pers.jwt.demo.dto.UserLoginDto;
import tw.pers.jwt.demo.entity.UserDetail;
import tw.pers.jwt.demo.service.UserDetailService;

@RequiredArgsConstructor
@RequestMapping("user")
@RestController
public class UserDetailController {
    private final UserDetailService userDetailService;

    @PostMapping("/login")
    public void userLogin(@RequestBody @Valid UserLoginDto userLoginDto, HttpServletResponse response) {
        userDetailService.userSignIn(userLoginDto, response);
    }

    @GetMapping("findUser/{username}")
    public UserDetail findUserByUsername(@PathVariable("username") String username) {
        UserDetail user = userDetailService.userByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not exit");
        }
        return user;
    }

}
