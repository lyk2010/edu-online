package com.kevin.online.eduservice.controller;

import com.kevin.online.common.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author kevin
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(description = "登录接口api")
public class LoginController {
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("/info")
    public R info(){
        return R.ok()
                .data("roles","[admin]")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
