package com.itheima.controller;
import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.apache.el.parser.Token;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")  //共同前缀是/user
@Validated //有效性检验
public class UserController {

    //调用service层对象，下面调用方法
    @Autowired
    private UserService userService;

    //redis要用
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //注册
    @PostMapping("/register")     //请求方式是post  路径是user/register
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        //查询用户
        User u=userService.findByUserName(username);
        if(u==null){
            //没有占用
            userService.register(username,password);
            return Result.success();
        }
        else{
            //占用了
            return Result.error("用户名已被占用");
        }
    }

    //登录
    @PostMapping("/login")     //请求方式是post  路径是user/login
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        //查询用户
        User u = userService.findByUserName(username);
        //1.用户是否存在
        if(u==null){
            return Result.error("用户不存在");
        }
        //2.密码是否正确
        if(Md5Util.getMD5String(password).equals(u.getPassword())){
            //登陆成功 +令牌token
            //创建集合，存放自定义信息
            Map<String,Object> claims=new HashMap<>();
            //将id和username存放到claims集合的2有效载荷部分    ---这个位置可以添加很多信息[解析出来的也多]
            claims.put("id",u.getId());
            claims.put("username",u.getUsername());
            //生成token令牌
            String token=JwtUtil.genToken(claims);

            //优化点   -- 将token存储到redis中
            ValueOperations<String, String> op = stringRedisTemplate.opsForValue();
            //token作为键和值 过期时间和JWT设置时间保持一致
            op.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);  //成功就返回token字符串
        }
        //用户存在但是密码错误 就到这一步
        return Result.error("密码错误");
    }

    //获取用户详细信息
    @GetMapping("/userInfo")   //请求方式是get  路径是user/userInfo
    public Result<User> userInfo(/*@RequestHeader(name="Authorization")String token*/){  //根据令牌反向获取用户id和username
        //1.自己通过令牌获取
//        //1.根据token令牌获取username
//        Map<String, Object> claims = JwtUtil.parseToken(token);
//        //获取2有效载荷部分自定义的id和username信息
//        int id= (int) claims.get("id");
//        String username= (String) claims.get("username");
//        System.out.println("进入用户详细信息页面,展示id和username具体内容如下：");
//        System.out.println("id:"+id);
//        System.out.println("username:"+username);
        //2.通过在拦截器配置ThreadLoader信息，来获取
        Map<String,Object> map= ThreadLocalUtil.get();
        String username = (String) map.get("username");
        //2.根据username获取用户信息
        User user = userService.findByUserName(username);
        //3.将User传给浏览器
        return Result.success(user);
    }

    //更新用户基本信息(除了头像和密码)
    @PutMapping("/update")  //请求方式是pust 路径是user/update
    public Result update(@RequestBody @Validated User user){  //@Validated对实体类里面的@NotNull  @NotEmpty @Email地址校验
        userService.update(user);  //调用service层方法
        //返回成功信息
        return Result.success();
    }

    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){   //@URL对url地址校验
        userService.updateAvatar(avatarUrl);  //调用service层对象方法
        //返回成功信息
        return Result.success();
    }

    //更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){  //因为这次传入的json和User不一致 所以要用Map集合  --> Springmvc会自动将json转为map集合
        //1.获取三个参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        //2.参数校验(传入的三个参数是否为空)
        if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){  //如果这三个参数有一个为空
            return Result.error("缺少必要的参数");
        }
        //3.校验原密码是否正确(查看原密码和old_pwd进行比对)
        //获取username
        Map<String,Object> map=ThreadLocalUtil.get();
        String username= (String) map.get("username");
        //放入根据username获取user的方法
        User user = userService.findByUserName(username);
        //获取user在数据库中的密码
        String password = user.getPassword();
        //对比原密码和传入的旧密码
        if(!password.equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("您输入的原密码不正确");
        }
        //4.校验新密码和重复新密码是否一致
        if(!newPwd.equals(rePwd)){
            return Result.error("您输入的新密码和再次确认的新密码不一致");
        }
        //5.调用service层对象方法完成密码更新
        userService.updatePwd(newPwd);

        //优化点  如果改了密码就删除token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        //删除token
        operations.getOperations().delete(token);
        //返回成功信息
        return Result.success();
    }

}