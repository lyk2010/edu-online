package com.kevin.online.ucenter.utils;

import com.kevin.online.ucenter.entity.EduUcenter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @author kevin
 * @version 1.0
 */
public class JwtUtils {

    public static final String SUBJECT = "edu";

    /**
     * 秘钥
     */
    public static final String APPSECRET = "edu";

    /**
     * 过期时间，毫秒，30分钟
     */
    public static final long EXPIRE = 1000 * 60 * 30;

    /**
     * 1.生成jwt token
     *
     * @param eduUcenter
     * @return
     */
    public static String geneJsonWebToken(EduUcenter eduUcenter) {
        if (eduUcenter == null
                || StringUtils.isEmpty(eduUcenter.getId())
                || StringUtils.isEmpty(eduUcenter.getNickname())
                || StringUtils.isEmpty(eduUcenter.getAvatar())) {
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", eduUcenter.getId())
                .claim("nickname", eduUcenter.getNickname())
                .claim("avatar", eduUcenter.getAvatar())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
        return token;
    }


    /**
     * 2.校验 jwt token
     *
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {
        return Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
    }


}
