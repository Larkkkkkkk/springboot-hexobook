package com.itheima;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    //生成jwt
    @Test
    public void testGen(){
        //集合存放2部分的自定义信息
        Map<String,Object> claims=new HashMap<>();
        claims.put("id","1");
        claims.put("username","张三");
        //创建一个JWT
        String token=JWT.create()
                .withClaim("user",claims) //添加2部分有效载荷(添加{"id":"1","username":"张三"})
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*3))   //添加过期时间(过一段时间就要重新登录)
                .sign(Algorithm.HMAC256("itheima"));  //添加1部分加密算法和3部分密钥(itheima是密钥)
        System.out.println(token);
    }

    //验证jwt
    @Test
    public void testParse(){
        //定义字符串  模拟用户传递过来的token
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiMSIsInVzZXJuYW1lIjoi5byg5LiJIn0sImV4cCI6MTcwMjk2MTE4N30.JvBIKVbPw-2OmTrvrMc2bBEvcLDp7MGS-t3-tt8LI_4";
        //添加加密算法和密钥。生成一个JWT验证
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("itheima")).build();
        //验证token，生成一个解析后的JWT对象
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        //反向获取claims集合
        Map<String, Claim> claims = decodedJWT.getClaims();
        //获取name="user"的有效载荷信息
        Claim user = claims.get("user");
        System.out.println(user); // {"id":"1","username":"张三"}
    }

}
