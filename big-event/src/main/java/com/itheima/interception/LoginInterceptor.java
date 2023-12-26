package com.itheima.interception;

import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Map;

//拦截器
@Component
public class LoginInterceptor implements HandlerInterceptor {

    //redis要用
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //根据名字为Authorization的header头获取token
        String token = request.getHeader("Authorization");
        try {
            //优化点  --redis(从原来直接解析，到现在需要有个门卫看一下对不对)!!!
            //从redis中获取相同token
            ValueOperations<String, String> operations =stringRedisTemplate.opsForValue();
            //get获取
            String redistoken = operations.get(token);

            //1.如果获取不到token  说明过期了要重新登陆
            if(redistoken==null){
                throw new RuntimeException();
            }

            //进行JWT解析token
            Map<String, Object> claims = JwtUtil.parseToken(token);
            System.out.println("此轮拦截器存放的有效载荷claim信息里面(当前登录人信息) ---> id是:"+claims.get("id")+" username是:"+claims.get("username"));

            //优化点  --新增ThreadLoader功能!!!
            // 把业务数据存放到ThreadLocal中
            ThreadLocalUtil.set(claims);

            return true;  //放行
        } catch (Exception e) {
            //设置http响应状态码为401
            response.setStatus(401);
            return false;  //不放行
        }
    }

    //清空ThreadLoader中的数据 --防止内存泄露
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }

}
