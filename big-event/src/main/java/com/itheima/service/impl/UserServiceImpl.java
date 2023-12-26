package com.itheima.service.impl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;

//Service注解将controller中autowired调用的实体注入到容器中  --就可以找到了
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User u=userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //密码加密 MD5加密
        String md5String = Md5Util.getMD5String(password);
        //添加
        userMapper.add(username,md5String);
    }

    @Override
    public void update(User user) {
        //更新更改时间  --也可以mapper层直接调用now()方法
        //user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);  //调用mapper层方法
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        //根据拦截器存的ThreadLocal取出来id
        Map<String,Object> map = ThreadLocalUtil.get();
        int id = (int) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        //根据拦截器存的ThreadLocal取出来id
        Map<String,Object> map = ThreadLocalUtil.get();
        int id = (int) map.get("id");
        //一定要记得把密码加密
        //密码加密 MD5加密
        String md5String = Md5Util.getMD5String(newPwd);
        userMapper.updatePwd(md5String,id);
    }

}
