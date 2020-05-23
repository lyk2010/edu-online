package com.kevin.online.ucenter.controller;


import com.kevin.online.common.R;
import com.kevin.online.ucenter.entity.EduUcenter;
import com.kevin.online.ucenter.service.EduUcenterService;
import com.kevin.online.ucenter.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kevin
 * @since 2020-05-18
 */
@Api(description = "用户相关接口api")
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class EduUcenterController {

    @Autowired
    private EduUcenterService eduUcenterService;


    @ApiOperation(value = "根据token获取用户信息",notes = "根据token获取用户信息")
    @GetMapping("/getEduUcenterByToken/{token}")
    public R getEduUcenterByToken(@PathVariable("token") String token) {
        Claims claims = JwtUtils.checkJWT(token);
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");
        String id = (String) claims.get("id");
        EduUcenter ucenter = new EduUcenter();
        ucenter.setId(id);
        ucenter.setAvatar(avatar);
        ucenter.setNickname(nickname);
        return R.ok().data("ucenter",ucenter);
    }




    @ApiOperation(value = "统计某一天的注册人数",notes = "统计某一天的注册人数")
    @GetMapping("/countRegisterNum/{day}")
    public R countRegisterNum(@PathVariable("day") String day) {
        Integer countNum = eduUcenterService.countRegisterNum(day);
        return R.ok().data("countNum",countNum);
    }





}

