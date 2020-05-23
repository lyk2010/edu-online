package com.kevin.online.ucenter.controller;

import com.google.gson.Gson;
import com.kevin.online.ucenter.entity.EduUcenter;
import com.kevin.online.ucenter.service.EduUcenterService;
import com.kevin.online.ucenter.utils.ConstantPropertiesUtil;
import com.kevin.online.ucenter.utils.HttpClientUtils;
import com.kevin.online.ucenter.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author kevin
 */
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    @Autowired
    private EduUcenterService ucenterService;

    /**
     * 扫描二维码进行回调
     */
    @GetMapping("/callback")
    public String callback(String code, String state) {
        System.out.println(code);
        System.out.println(state);
        //获取扫描人的个人信息
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //拼接地址
        String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET, code);
        //发送httpclient请求，获取凭证
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //把返回的result使用gson进行数据转换
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        //通过转换之后的Map,根据map的键获取值
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");

        EduUcenter ucenter = ucenterService.existWxUserByOpenid(openid);
        if (ucenter == null) {
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" + "?access_token=%s" + "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            //发送请求，获取微信个人信息
            String userInfo = null;
            try {
                userInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            /**
             * 微信昵称
             */
            String nickname = (String) userInfoMap.get("nickname");
            /**
             * 微信头像
             */
            String headimgurl = (String) userInfoMap.get("headimgurl");
            //把微信用户信息添加到数据库中
            ucenter = new EduUcenter();
            ucenter.setNickname(nickname);
            ucenter.setOpenid(openid);
            ucenter.setAvatar(headimgurl);
            ucenterService.save(ucenter);
        }
        //根据对象生成jwt令牌
        String token = JwtUtils.geneJsonWebToken(ucenter);

        //重定向到前台页面中去
        return "redirect:http://localhost:3000?token="+token;
    }

    /**
     * 生成微信登录扫描的二维码
     */
    @GetMapping("/login")
    public String getQrConnect(HttpSession session) {
        //微信开放平台授权baseURL
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //获取扫描后回调的地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;

        //对回调地址进行编码
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO 为了测试：这个值传递是注册内网穿透的域名名称，实现域名跳转
        String state = "atonline";
        //拼接最终生成二维码地址
        String qrCodeUrl = String.format(baseUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID, redirectUrl, state);
        //重定向一个地址
        return "redirect:" + qrCodeUrl;
    }
}
