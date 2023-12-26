package com.itheima.mapper;

import com.itheima.pojo.User;
import com.itheima.utils.ThreadLocalUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

//Mapper注解将ServiceImpl中autowired调用的实体注入到容器中  --就可以找到了
@Mapper
public interface UserMapper {

    //查询用户
    @Select("select * from user where username=#{username}")
    public User findByUserName(String username);

    @Insert("insert into user(username,password,create_time,update_time) values (#{username},#{password},now(),now())")
    //添加用户
    public void add(String username, String password);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=now() where id=#{id}")
    void update(User user);

    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,int id);

    @Update("update user set password=#{md5String},update_time=now() where id=#{id}")
    void updatePwd(String md5String,int id);

}
