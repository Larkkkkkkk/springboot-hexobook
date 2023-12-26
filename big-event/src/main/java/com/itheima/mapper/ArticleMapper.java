package com.itheima.mapper;

import com.itheima.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    //新增文章
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) values (#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},now(),now())")
    void add(Article article);

    //分页查询(动态sql卸载mybatis映射)
    List<Article> list(Integer userId, String categoryId, String state);

    //根据id获取文章详情
    @Select("select * from article where id=#{id}")
    Article detail(Integer id);

    //更新文章
    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},create_user=#{createUser},update_time=now() where id=#{id}")
    void update(Article article);

    //删除文章
    @Delete("delete from article where id=#{id}")
    void delete(Integer id);
}
