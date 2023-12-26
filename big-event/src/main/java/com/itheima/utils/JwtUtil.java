package com.itheima.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "itheima";
	
	//接收业务数据,生成token并返回
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)  //添加2部分有效载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12)) //添加过期时间(过一段时间就要重新登陆)
                .sign(Algorithm.HMAC256(KEY));  //添加1部分加密算法和3部分密钥
    }

	//接收token,验证token,并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY)) //根据秘钥生成
                .build()  //生成一个JWT验证
                .verify(token) //验证token 生成一个解析后的JWT对象
                .getClaim("claims")  //反向获取有效载荷里面的自定义信息
                .asMap();
    }

}
