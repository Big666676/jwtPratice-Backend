package tw.pers.jwt.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("info")
@RestController
public class APICheckController{

    @GetMapping("check")
    public String check(){
        return "api check";
    }

}
